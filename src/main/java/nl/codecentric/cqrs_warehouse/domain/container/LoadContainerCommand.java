package nl.codecentric.cqrs_warehouse.domain.container;

import java.util.UUID;

import lombok.Builder;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Data;

@Data
@Builder
public class LoadContainerCommand {
    @TargetAggregateIdentifier
    private final UUID articleId;
    private final UUID containerId;
    private final UUID shipmentId;
}
