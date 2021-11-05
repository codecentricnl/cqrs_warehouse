package nl.codecentric.cqrs_warehouse.projections;

import nl.codecentric.cqrs_warehouse.domain.container.*;
import nl.codecentric.cqrs_warehouse.domain.shipment.ShipmentDeparturedEvent;
import nl.codecentric.cqrs_warehouse.repositories.ContainerDTO;
import nl.codecentric.cqrs_warehouse.repositories.ContainerRepository;
import org.axonframework.config.ProcessingGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@ProcessingGroup("ContainerProjection")
public class ContainerProjection {

    @Autowired
    ContainerRepository containerRepository;

    public void on(ContainerUnloadedEvent event) {
    }

    public void on(ContainerMovedEvent event) {
    }

    public void on(ContainerLoadedEvent event) {
    }

    public void on(ContainerClaimedEvent event) {
    }

    public void on(ContainerUnclaimedEvent event) {
    }

    public void on(ContainerExpiredEvent event) {
    }

    public void on(ShipmentDeparturedEvent event) {
    }

    public List<ContainerDTO> on(FetchAllContainersQuery query) {
        return null;
    }

    public Optional<ContainerDTO> on(FetchContainerByIdQuery query) {
        return null;
    }
}
