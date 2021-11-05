package nl.codecentric.cqrs_warehouse.domain.container;

import java.util.UUID;

import lombok.Data;

@Data
public class ContainerExpiredEvent {
    private final UUID containerId;
}
