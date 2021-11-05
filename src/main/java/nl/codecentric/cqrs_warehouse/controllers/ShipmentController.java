package nl.codecentric.cqrs_warehouse.controllers;

import nl.codecentric.cqrs_warehouse.domain.shipment.CreateShipmentCommand;
import nl.codecentric.cqrs_warehouse.domain.shipment.InitialiseShipmentCommand;
import nl.codecentric.cqrs_warehouse.repositories.ShipmentDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController()
public class ShipmentController {

    @Autowired
    CommandGateway commandGateway;

    @Autowired
    QueryGateway queryGateway;

    @PostMapping(path = "/shipments/create")
    public UUID createShipment(@RequestBody CreateShipmentCommand command) {
        return null;
    }

    @GetMapping(path = "/shipments")
    public CompletableFuture<List<ShipmentDTO>> getShipments() {
        return null;
    }

    @GetMapping(path = "/shipments/{shipmentId}")
    public CompletableFuture<Optional<ShipmentDTO>> getShipment(@PathVariable("shipmentId") String shipmentId) {
        return null;
    }

    @PostMapping(path = "/shipments/initialise")
    public UUID initialiseShipment(@RequestBody InitialiseShipmentCommand command) {
        return null;
    }

    @PostMapping(path = "/shipments/{shipmentId}/departure")
    public void departShipment(@PathVariable("shipmentId") String shipmentId) {
    }

}
