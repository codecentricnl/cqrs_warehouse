package nl.codecentric.cqrs_warehouse.projections;

import nl.codecentric.cqrs_warehouse.domain.container.ContainerUnloadedEvent;
import nl.codecentric.cqrs_warehouse.domain.shipment.*;
import nl.codecentric.cqrs_warehouse.repositories.*;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@ProcessingGroup("ShipmentProjection")
public class ShipmentProjection {

    @Autowired
    ShipmentRepository shipmentRepository;

    @EventHandler
    public void on(ShipmentCreatedEvent event) {
        shipmentRepository.save(ShipmentDTO.builder()
                .shipmentId(event.getShipmentId().toString())
                .customerName(event.getCustomerName())
                .volume(event.getVolume())
                .articleId(event.getArticleId().toString())
                .state(event.getState())
                .build());
    }

    @EventHandler
    public void on(ContainerUnloadedEvent event) {
        Optional<ShipmentDTO> shipment = shipmentRepository.findById(event.getShipmentId().toString());

        if (shipment.isPresent()) {
            ShipmentDTO shipmentDTO = shipment.get().toBuilder().volume(shipment.get().getVolume() - 1).build();
            shipmentRepository.save(shipmentDTO);
            if(shipmentDTO.getVolume() <= 0) {
                shipmentRepository.deleteById(shipmentDTO.getShipmentId());
            }
        }
    }

    @EventHandler
    public void on(ShipmentClaimedEvent event) {
        Optional<ShipmentDTO> shipment = shipmentRepository.findById(event.getShipmentId().toString());

        if (shipment.isPresent()) {
            ShipmentDTO shipmentDTO = shipment.get().toBuilder().state(event.getState()).build();
            shipmentRepository.save(shipmentDTO);
        }
    }

    @EventHandler
    public void on(ShipmentResolvedEvent event) {
        Optional<ShipmentDTO> shipment = shipmentRepository.findById(event.getShipmentId().toString());

        if (shipment.isPresent()) {
            ShipmentDTO shipmentDTO = shipment.get().toBuilder().state(event.getState()).build();
            shipmentRepository.save(shipmentDTO);
        }
    }

    @EventHandler
    public void on(ShipmentCanceledEvent event) {
        Optional<ShipmentDTO> shipment = shipmentRepository.findById(event.getShipmentId().toString());

        if (shipment.isPresent()) {
            ShipmentDTO shipmentDTO = shipment.get().toBuilder().state(event.getState()).build();
            shipmentRepository.save(shipmentDTO);
        }
    }

    @EventHandler
    public void on(ShipmentDeparturedEvent event) {
        shipmentRepository.deleteById(event.getShipmentId().toString());
    }

    @QueryHandler
    public List<ShipmentDTO> on(FetchAllShipmentsQuery query) {
        return shipmentRepository.findAll();
    }

    @QueryHandler
    public Optional<ShipmentDTO> on(FetchShipmentByIdQuery query) {
        return shipmentRepository.findById(query.getShipmentId());
    }
}
