package edu.agh.atyranski.rabbitmq.space.provider;

import edu.agh.atyranski.rabbitmq.space.model.JobDefinition;
import edu.agh.atyranski.rabbitmq.space.model.JobStatus;
import edu.agh.atyranski.rabbitmq.space.model.ServiceType;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class InputJobProvider implements JobProvider {

    private final String agencyName;
    private final Scanner scanner;

    private InputJobProvider(String agencyName) {
        this.agencyName = agencyName;
        this.scanner = new Scanner(System.in);
    }

    public static InputJobProvider create(String agencyName) {
        return new InputJobProvider(agencyName);
    }

    @Override
    public boolean hasNextJob() {
        while (true) {
            System.out.print("Next job? [Y/N]: ");
            String next = scanner.nextLine();

            if (next.equalsIgnoreCase("Y") || next.equalsIgnoreCase("YES")) {
                return true;
            }

            if (next.equalsIgnoreCase("N") || next.equalsIgnoreCase("NO")) {
                return false;
            }

            System.out.println("Incorrect answer.");
        }
    }

    @Override
    public JobDefinition nextJob() {
        System.out.print("Service type: ");
        String service = scanner.nextLine();
        ServiceType serviceType = null;

        while (serviceType == null) {
            try {
                serviceType = ServiceType.valueOf(service);
            } catch (IllegalArgumentException e) {
                System.out.printf("No such service. (available: %s )",
                        String.join(",", Arrays.stream(ServiceType.values())
                                .distinct()
                                .map(Enum::toString)
                                .collect(Collectors.joining(","))));
            }
        }

        return JobDefinition.builder()
                .orderingAgencyName(agencyName)
                .serviceType(serviceType)
                .jobStatus(JobStatus.PENDING)
                .build();
    }
}
