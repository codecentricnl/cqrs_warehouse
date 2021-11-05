package nl.codecentric.cqrs_warehouse.domain.shipment;

import java.util.UUID;

import lombok.Builder;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
@Builder
public class CreateShipmentCommand {
    @TargetAggregateIdentifier
    UUID shipmentId;
    String customerName;
    Integer volume;
    UUID articleId;
}
