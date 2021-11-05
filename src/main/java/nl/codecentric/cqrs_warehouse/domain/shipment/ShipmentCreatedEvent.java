package nl.codecentric.cqrs_warehouse.domain.shipment;

import java.util.UUID;

import lombok.Data;

@Data
public class ShipmentCreatedEvent {
    private final UUID shipmentId;
    private final String customerName;
    private final Integer volume;
    private final UUID articleId;
    private final String state;
}
