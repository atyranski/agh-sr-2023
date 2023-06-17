package edu.agh.atyranski.rabbitmq.space.agency;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import edu.agh.atyranski.rabbitmq.space.agency.config.SpaceAgencyConfig;
import edu.agh.atyranski.rabbitmq.space.agency.listener.AcknowledgementListener;
import edu.agh.atyranski.rabbitmq.space.util.QueueNameParser;
import edu.agh.atyranski.rabbitmq.space.model.JobDefinition;
import edu.agh.atyranski.rabbitmq.space.provider.InputJobProvider;
import edu.agh.atyranski.rabbitmq.space.provider.JobProvider;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@Slf4j
public class SpaceAgencyApplication {

    private final SpaceAgencyConfig config;
    private final JobProvider jobProvider;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private AcknowledgementListener acknowledgementListener;

    private SpaceAgencyApplication(SpaceAgencyConfig config) {
        this.config = config;
        this.jobProvider = getJobProvider();
    }

    private JobProvider getJobProvider() {
        switch (config.getProviderMode()) {
            default:
                return InputJobProvider.create(config.getName());
        }
    }

    private Channel init(Connection connection) throws IOException {
        Channel channel = connection.createChannel();
        log.info("[Start] Connection established");

        channel.exchangeDeclare(config.getExchangeName(), BuiltinExchangeType.TOPIC);
        log.info("[Start] Exchange '{}' of type TOPIC declared", config.getExchangeName());

        createAcknowledgementQueue(channel);

        return channel;
    }

    private void createAcknowledgementQueue(Channel channel) throws IOException {
        String acknowledgementQueueName = channel.queueDeclare().getQueue();
        channel.queueBind(
                acknowledgementQueueName,
                config.getExchangeName(),
                QueueNameParser.getAcknowledgementQueueName(config.getName()));
        log.info("[Start] Successfully bound to acknowledgement queue");

        this.acknowledgementListener = AcknowledgementListener.create(channel, acknowledgementQueueName);
        acknowledgementListener.start();
        log.info("[Start] Acknowledgement listener started");
    }

    private void requestJob(Channel channel, JobDefinition job)
            throws IOException {

        byte[] message = objectMapper.writeValueAsString(job).getBytes(StandardCharsets.UTF_8);
        String queue = QueueNameParser.getAgencyServiceQueueName(config.getName(), job.getServiceType());

        channel.basicPublish(config.getExchangeName(), queue, null, message);
        log.info("[Request] job '{}/{}' has been requested", job.getId(), job.getServiceType().toString());
    }

    private void start() {
        log.info("[Start] Starting space agency '{}'", config.getName());
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(config.getHost());

        try (Connection connection = connectionFactory.newConnection()) {
            Channel channel = init(connection);

            while (jobProvider.hasNextJob()) {
                JobDefinition currentJob = jobProvider.nextJob();
                log.info("[Preparing] job '{}/{} is being prepared'",
                        currentJob.getId(), currentJob.getServiceType().toString());

                requestJob(channel, currentJob);
            }

        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    private void stop() throws InterruptedException {
        acknowledgementListener.join();
    }

    public static void main(String[] args)
            throws InterruptedException {

        final SpaceAgencyConfig config = SpaceAgencyConfig.fromArgs(args);
        final SpaceAgencyApplication application = new SpaceAgencyApplication(config);

        try {
            application.start();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            application.stop();
        }
    }
}
