package nl.codecentric.cqrs_warehouse.controllers;

import nl.codecentric.cqrs_warehouse.domain.shipment.*;
import nl.codecentric.cqrs_warehouse.repositories.ShipmentDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
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
        commandGateway.sendAndWait(command);
        return command.getShipmentId();
    }

    @GetMapping(path = "/shipments")
    public CompletableFuture<List<ShipmentDTO>> getShipments() {
        return queryGateway.query(new FetchAllShipmentsQuery(), ResponseTypes.multipleInstancesOf(ShipmentDTO.class));
    }

    @GetMapping(path = "/shipments/{shipmentId}")
    public CompletableFuture<Optional<ShipmentDTO>> getShipment(@PathVariable("shipmentId") String shipmentId) {
        return queryGateway.query(new FetchShipmentByIdQuery(shipmentId), ResponseTypes.optionalInstanceOf(ShipmentDTO.class));
    }

    @PostMapping(path = "/shipments/initialise")
    public UUID initialiseShipment(@RequestBody InitialiseShipmentCommand command) {
        commandGateway.sendAndWait(command);
        return command.getShipmentId();
    }

    @PostMapping(path = "/shipments/{shipmentId}/departure")
    public void departShipment(@PathVariable("shipmentId") String shipmentId) {
        commandGateway.sendAndWait(new DepartureShipmentCommand(UUID.fromString(shipmentId)));
    }

}
