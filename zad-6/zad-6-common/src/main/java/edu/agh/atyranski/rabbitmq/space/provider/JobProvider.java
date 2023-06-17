package edu.agh.atyranski.rabbitmq.space.provider;

import edu.agh.atyranski.rabbitmq.space.model.JobDefinition;

public interface JobProvider {
    boolean hasNextJob();

    JobDefinition nextJob();
}
