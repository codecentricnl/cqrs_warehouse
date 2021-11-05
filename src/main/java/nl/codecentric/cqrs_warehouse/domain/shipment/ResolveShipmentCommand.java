package nl.codecentric.cqrs_warehouse.domain.shipment;

import java.util.UUID;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Data;

@Data
public class ResolveShipmentCommand {
    @TargetAggregateIdentifier
    private final UUID shipmentId;
    private final String state;
}
