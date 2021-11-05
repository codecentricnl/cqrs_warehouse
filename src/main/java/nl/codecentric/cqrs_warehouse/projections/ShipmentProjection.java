package nl.codecentric.cqrs_warehouse.projections;

import nl.codecentric.cqrs_warehouse.domain.container.ContainerUnloadedEvent;
import nl.codecentric.cqrs_warehouse.domain.shipment.*;
import nl.codecentric.cqrs_warehouse.repositories.ShipmentDTO;
import nl.codecentric.cqrs_warehouse.repositories.ShipmentRepository;
import org.axonframework.config.ProcessingGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@ProcessingGroup("ShipmentProjection")
public class ShipmentProjection {

    @Autowired
    ShipmentRepository shipmentRepository;

    public void on(ShipmentCreatedEvent event) {
    }

    public void on(ContainerUnloadedEvent event) {
    }

    public void on(ShipmentClaimedEvent event) {
    }

    public void on(ShipmentResolvedEvent event) {
    }

    public void on(ShipmentCanceledEvent event) {
    }

    public void on(ShipmentDeparturedEvent event) {
    }

    public List<ShipmentDTO> on(FetchAllShipmentsQuery query) {
        return null;
    }

    public Optional<ShipmentDTO> on(FetchShipmentByIdQuery query) {
        return null;
    }
}
