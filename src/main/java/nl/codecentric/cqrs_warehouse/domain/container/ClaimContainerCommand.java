package nl.codecentric.cqrs_warehouse.domain.container;

import java.util.UUID;

import lombok.Builder;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Data;

@Value
@Builder
public class ClaimContainerCommand {
    @TargetAggregateIdentifier
    UUID articleId;
    UUID shipmentId;
}
