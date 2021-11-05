package nl.codecentric.cqrs_warehouse.domain.shipment;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ShipmentAggregate {
    private UUID shipmentId;
    private String customerName;
    private Integer volume;
    private UUID articleId;
    private String state;

    public ShipmentAggregate(CreateShipmentCommand command) {
    }

    public void on(ShipmentCreatedEvent event) {
    }

    public void handle(ClaimShipmentCommand command) {
    }

    public void on(ShipmentClaimedEvent event) {
    }

    public void handle(ResolveShipmentCommand command) {
    }

    public void on(ShipmentResolvedEvent event) {
    }

    public void on(DepartureShipmentCommand command) {
    }

    public void on(CancelShipmentCommand command) {
    }
}
