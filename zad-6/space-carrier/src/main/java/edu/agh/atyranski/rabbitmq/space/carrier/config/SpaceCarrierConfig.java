package edu.agh.atyranski.rabbitmq.space.carrier.config;

import edu.agh.atyranski.rabbitmq.space.model.ServiceType;
import edu.agh.atyranski.rabbitmq.space.processor.JobProcessorType;
import edu.agh.atyranski.rabbitmq.space.util.ConfigParser;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

@Builder
@Getter
public class SpaceCarrierConfig {

    private final static String NAME_CONFIG = "name";
    private final static String HOST_CONFIG = "host";
    private final static String EXCHANGE_NAME_CONFIG = "exchange-name";
    private final static String QOS_CONFIG = "qos";
    private final static String JOB_PROCESSOR_CONFIG = "job-processor";

    private final String name;
    private final String host;
    private final String exchangeName;
    private final int qos;
    private final JobProcessorType processorMode;
    private final Set<ServiceType> providedServices;

    public static SpaceCarrierConfig fromArgs(String[] args) {
        Properties properties = ConfigParser.parseArguments(args);

        return SpaceCarrierConfig.builder()
                .name(properties.getProperty(NAME_CONFIG))
                .host(properties.getProperty(HOST_CONFIG))
                .exchangeName(properties.getProperty(EXCHANGE_NAME_CONFIG))
                .qos(Integer.parseInt(properties.getProperty(QOS_CONFIG)))
                .processorMode(JobProcessorType.valueOf(properties.getProperty(JOB_PROCESSOR_CONFIG)))
                .build();
    }

    public static SpaceCarrierConfig getExample() {
        return SpaceCarrierConfig.builder()
                .name("Carrier-A")
                .host("localhost")
                .exchangeName("space.topic")
                .qos(1)
                .processorMode(JobProcessorType.BASIC)
                .providedServices(Set.of(
                        ServiceType.PASSENGER_TRANSPORT,
                        ServiceType.PLACE_SATELLITE_IN_ORBIT
                ))
                .build();
    }
}
