package nl.codecentric.cqrs_warehouse.domain.shipment;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Data
public class CancelShipmentCommand {
    @TargetAggregateIdentifier
    private final UUID shipmentId;
    private final String state;
}
