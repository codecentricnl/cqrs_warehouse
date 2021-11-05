package nl.codecentric.cqrs_warehouse.domain.shipment;

import lombok.Data;

@Data
public class FetchShipmentByIdQuery {
    final String shipmentId;
}
