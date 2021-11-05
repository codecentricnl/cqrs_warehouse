package nl.codecentric.cqrs_warehouse.domain.container;

import java.util.UUID;

import lombok.Data;

@Data
public class ContainerClaimedEvent {
    private final UUID articleId;
    private final UUID containerId;
    private final UUID shipmentId;
}
