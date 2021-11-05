package nl.codecentric.cqrs_warehouse.domain.container;

import java.time.Instant;
import java.util.UUID;

import lombok.Builder;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UnloadContainerCommand {
    @TargetAggregateIdentifier
    private UUID articleId;
    private UUID shipmentId;
    private UUID containerId;
    private String location;
    private Instant expirationDate;
}
