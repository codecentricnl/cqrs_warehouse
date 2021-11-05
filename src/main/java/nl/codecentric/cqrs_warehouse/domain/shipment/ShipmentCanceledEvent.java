package nl.codecentric.cqrs_warehouse.domain.shipment;

import lombok.Data;

import java.util.UUID;

@Data
public class ShipmentCanceledEvent {
    private final UUID shipmentId;
    private final String state;
}
