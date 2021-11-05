package nl.codecentric.cqrs_warehouse.domain.shipment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartureShipmentCommand {
    private UUID shipmentId;
}
