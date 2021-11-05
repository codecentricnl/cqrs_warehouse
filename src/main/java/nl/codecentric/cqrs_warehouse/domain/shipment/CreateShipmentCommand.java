package nl.codecentric.cqrs_warehouse.domain.shipment;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class CreateShipmentCommand {
    UUID shipmentId;
    String customerName;
    Integer volume;
    UUID articleId;
}
