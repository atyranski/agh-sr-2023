package edu.agh.atyranski.rabbitmq.space.processor;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

public interface JobProcessor {

    DeliverCallback getDeliveryCallback(Channel channel);
}
