package nl.codecentric.cqrs_warehouse.controllers;

import nl.codecentric.cqrs_warehouse.domain.container.FetchAllContainersQuery;
import nl.codecentric.cqrs_warehouse.domain.container.FetchContainerByIdQuery;
import nl.codecentric.cqrs_warehouse.repositories.ContainerDTO;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController()
public class ContainerController {

    @Autowired
    QueryGateway queryGateway;

    @GetMapping(path = "/containers")
    public CompletableFuture<List<ContainerDTO>> getAllContainers() {
        return queryGateway.query(new FetchAllContainersQuery(), ResponseTypes.multipleInstancesOf(ContainerDTO.class));
    }

    @GetMapping(path = "/containers/{containerId}")
    public CompletableFuture<Optional<ContainerDTO>> getContainer(@PathVariable("containerId") String containerId) {
        return queryGateway.query(new FetchContainerByIdQuery(containerId), ResponseTypes.optionalInstanceOf(ContainerDTO.class));
    }
}
