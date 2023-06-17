package edu.agh.atyranski.rabbitmq.space.agency.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import edu.agh.atyranski.rabbitmq.space.model.JobDefinition;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class AcknowledgementListener extends Thread {

    private final Channel channel;
    private final String acknowledgementQueueName;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private AcknowledgementListener(Channel channel, String acknowledgementQueueName) {
        log.info("Acknowledgement listener created");
        this.channel = channel;
        this.acknowledgementQueueName = acknowledgementQueueName;
    }

    public static AcknowledgementListener create(Channel channel, String acknowledgementQueueName) {
        return new AcknowledgementListener(channel, acknowledgementQueueName);
    }

    public DeliverCallback getDeliveryCallback(Channel channel) {
        return (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            JobDefinition jobDefinition = objectMapper.readValue(message, JobDefinition.class);

            log.info("[Response] Received job '{}'", jobDefinition.getShortForm());
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };
    }

    @Override
    public void run() {
        try {
            channel.basicConsume(
                    acknowledgementQueueName,
                    false,
                    getDeliveryCallback(channel),
                    consumerTag -> { });
        } catch (IOException e) {
            log.warn("Acknowledgement listener encountered error", e);
            throw new RuntimeException(e);
        }
    }
}
