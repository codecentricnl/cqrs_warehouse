package nl.codecentric.cqrs_warehouse.domain.container;

import lombok.Data;

import java.util.UUID;

@Data
public class ContainerUnclaimedEvent {
    private final UUID articleId;
    private final UUID containerId;
    private final UUID shipmentId;
}
