package nl.codecentric.cqrs_warehouse.domain.shipment;

import java.util.UUID;

import lombok.Data;

@Data
public class ShipmentResolvedEvent {
    private final UUID shipmentId;
    private final String state;
}
