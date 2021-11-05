package nl.codecentric.cqrs_warehouse.domain.shipment;

import java.util.UUID;

import lombok.Data;

@Data
public class ShipmentClaimedEvent {
    private final UUID shipmentId;
    private final String state;
}
