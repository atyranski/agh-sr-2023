package edu.agh.atyranski.rabbitmq.space.util;

import edu.agh.atyranski.rabbitmq.space.model.ServiceType;

public class QueueNameParser {

    private final static String EXCHANGE_ACKNOWLEDGEMENT_INFIX = "acknowledgement";
    private final static String SERVICE_INFIX = "service";

    public static String getCarrierAcknowledgementQueueName(String carrierName, String agencyName) {
        return String.format("%s.%s.%s", carrierName, EXCHANGE_ACKNOWLEDGEMENT_INFIX, agencyName);
    }

    public static String getAcknowledgementQueueName(String agencyName) {
        return getCarrierAcknowledgementQueueName("*", agencyName);
    }

    public static String getAgencyServiceQueueName(String agencyName, ServiceType serviceType) {
        return String.format("%s.%s.%s", agencyName, SERVICE_INFIX, serviceType.toString().toLowerCase());
    }

    public static String getServiceQueueName( ServiceType serviceType) {
        return getAgencyServiceQueueName("*", serviceType);
    }
}
