package nl.codecentric.cqrs_warehouse.domain.shipment;

import java.util.UUID;

import lombok.Data;

@Data
public class ShipmentInitialisedEvent {
    private final UUID shipmentId;
    private final String customerName;
    private final UUID articleId;
    private final Integer volume;
}
