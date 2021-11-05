package nl.codecentric.cqrs_warehouse.domain.container;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UnclaimContainerCommand {
    private final UUID articleId;
    private final UUID containerId;
    private final UUID shipmentId;
}
