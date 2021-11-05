package nl.codecentric.cqrs_warehouse.domain.shipment;

import lombok.Data;

import java.util.UUID;

@Data
public class ShipmentDeparturedEvent {
    private final UUID shipmentId;
}
