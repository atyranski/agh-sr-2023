package edu.agh.atyranski.rabbitmq.space.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import edu.agh.atyranski.rabbitmq.space.model.JobDefinition;
import edu.agh.atyranski.rabbitmq.space.model.JobStatus;
import edu.agh.atyranski.rabbitmq.space.util.QueueNameParser;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class BasicJobProcessor implements JobProcessor {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String exchangeName;
    private final String carrierName;

    private BasicJobProcessor(String exchangeName, String carrierName) {
        this.exchangeName = exchangeName;
        this.carrierName = carrierName;
    }

    public static JobProcessor create(String exchangeName, String carrierName) {
        return new BasicJobProcessor(exchangeName, carrierName);
    }

    @Override
    public DeliverCallback getDeliveryCallback(Channel channel) {
        return (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            JobDefinition jobDefinition = objectMapper.readValue(message, JobDefinition.class);
            jobDefinition.setJobStatus(JobStatus.PROCESSING);
            log.info("[Received] processing '{}'", jobDefinition.getShortForm());

            jobDefinition.setJobStatus(JobStatus.APPROVED);
            log.info("[Approved] successfully processed '{}'", jobDefinition.getShortForm());

            byte[] response = objectMapper.writeValueAsString(jobDefinition).getBytes(StandardCharsets.UTF_8);

            channel.basicPublish(
                    exchangeName,
                    QueueNameParser.getCarrierAcknowledgementQueueName(
                            carrierName,
                            jobDefinition.getOrderingAgencyName()),
                    null, response);
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };
    }
}
