package edu.agh.atyranski.rabbitmq.space.agency.config;

import edu.agh.atyranski.rabbitmq.space.processor.JobProcessorType;
import edu.agh.atyranski.rabbitmq.space.provider.JobProviderMode;
import edu.agh.atyranski.rabbitmq.space.util.ConfigParser;
import lombok.Builder;
import lombok.Getter;

import java.util.Properties;

@Builder
@Getter
public class SpaceAgencyConfig {

    private final static String NAME_CONFIG = "name";
    private final static String HOST_CONFIG = "host";
    private final static String EXCHANGE_NAME_CONFIG = "exchange-name";
    private final static String JOB_PROVIDER_CONFIG = "job-provider";

    private final String name;
    private final String host;
    private final String exchangeName;
    private final JobProviderMode providerMode;

    public static SpaceAgencyConfig fromArgs(String[] args) {
        Properties properties = ConfigParser.parseArguments(args);

        return SpaceAgencyConfig.builder()
                .name(properties.getProperty(NAME_CONFIG))
                .host(properties.getProperty(HOST_CONFIG))
                .exchangeName(properties.getProperty(EXCHANGE_NAME_CONFIG))
                .providerMode(JobProviderMode.valueOf(properties.getProperty(JOB_PROVIDER_CONFIG)))
                .build();
    }

    public static SpaceAgencyConfig getExample() {
        return SpaceAgencyConfig.builder()
                .name("Agency-A")
                .host("localhost")
                .exchangeName("space.topic")
                .providerMode(JobProviderMode.INPUT)
                .build();
    }

}
