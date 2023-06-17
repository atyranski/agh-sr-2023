package edu.agh.atyranski.rabbitmq.space.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobDefinition {

    private final String id = UUID.randomUUID().toString().substring(0, 8);
    private String orderingAgencyName;
    private String processingCarrierName;
    private ServiceType serviceType;
    private JobStatus jobStatus;

    public String getShortForm() {
        return String.format("%s/%s/%s/%s", id, orderingAgencyName, jobStatus, serviceType.toString());
    }
}
