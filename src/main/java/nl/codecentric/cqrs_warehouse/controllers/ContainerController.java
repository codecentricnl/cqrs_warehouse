package nl.codecentric.cqrs_warehouse.controllers;

import nl.codecentric.cqrs_warehouse.repositories.ContainerDTO;
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
        return null;
    }

    @GetMapping(path = "/containers/{containerId}")
    public CompletableFuture<Optional<ContainerDTO>> getContainer(@PathVariable("containerId") String containerId) {
        return null;
    }
}
