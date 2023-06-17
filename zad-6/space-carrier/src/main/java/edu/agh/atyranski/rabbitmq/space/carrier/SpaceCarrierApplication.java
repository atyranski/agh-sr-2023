package edu.agh.atyranski.rabbitmq.space.carrier;

import com.rabbitmq.client.*;
import edu.agh.atyranski.rabbitmq.space.carrier.config.SpaceCarrierConfig;
import edu.agh.atyranski.rabbitmq.space.model.ServiceType;
import edu.agh.atyranski.rabbitmq.space.processor.BasicJobProcessor;
import edu.agh.atyranski.rabbitmq.space.processor.JobProcessor;
import edu.agh.atyranski.rabbitmq.space.util.QueueNameParser;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Slf4j
public class SpaceCarrierApplication {

    private final SpaceCarrierConfig config;
    private final JobProcessor jobProcessor;

    private Connection connection;

    private SpaceCarrierApplication(SpaceCarrierConfig config) {
        this.config = config;
        this.jobProcessor = getJobProcessor(config);
    }

    private JobProcessor getJobProcessor(SpaceCarrierConfig config) {
        switch (config.getProcessorMode()) {
            default:
                return BasicJobProcessor.create(config.getExchangeName(), config.getName());
        }
    }

    private Channel init(Connection connection) throws IOException {
        Channel channel = connection.createChannel();
        log.info("[Start] Connection established");

        channel.exchangeDeclare(config.getExchangeName(), BuiltinExchangeType.TOPIC);
        log.info("[Start] Exchange '{}' of type TOPIC declared", config.getExchangeName());

        channel.basicQos(config.getQos());
        log.info("[Start] QOS set to: {}", config.getQos());

        return channel;
    }

    private void bindToQueues(Channel channel, DeliverCallback deliverCallback) throws IOException {
        for (ServiceType serviceType: config.getProvidedServices()) {
            String queueName = channel.queueDeclare(serviceType.toString().toLowerCase(),
                    false, false, false, null).getQueue();

            channel.queueBind(queueName, config.getExchangeName(), QueueNameParser.getServiceQueueName(serviceType));
            channel.basicConsume(queueName, false, deliverCallback, consumerTag -> { });
            log.info("[Start] successfully bound to queue '{}'", queueName);
        }
    }

    private void start() {
        log.info("[Start] Starting space carrier '{}'", config.getName());
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(config.getHost());

        try {
            this.connection = connectionFactory.newConnection();
            Channel channel = init(connection);
            DeliverCallback deliverCallback = jobProcessor.getDeliveryCallback(channel);

            bindToQueues(channel, deliverCallback);

            log.info("[Waiting] Listening for messages. To exit press CTRL + C");

        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }

    }

    private void stop() {
        try {
            this.connection.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        final SpaceCarrierConfig config = SpaceCarrierConfig.fromArgs(args);
        final SpaceCarrierApplication application = new SpaceCarrierApplication(config);

        try {
            application.start();
        } catch (Exception e) {
            application.stop();
            e.printStackTrace();
        }
    }
}
