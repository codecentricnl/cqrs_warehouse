package nl.codecentric.cqrs_warehouse.domain.container;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Data
@Builder
public class UnclaimContainerCommand {
    @TargetAggregateIdentifier
    private final UUID articleId;
    private final UUID containerId;
    private final UUID shipmentId;
}
