package nl.codecentric.cqrs_warehouse.domain.container;

import java.util.UUID;

import lombok.Data;

@Data
public class ContainerMovedEvent {
    private final UUID containerId;
    private final String location;
}
