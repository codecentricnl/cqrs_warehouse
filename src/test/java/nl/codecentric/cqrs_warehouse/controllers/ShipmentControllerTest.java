package nl.codecentric.cqrs_warehouse.controllers;

import nl.codecentric.cqrs_warehouse.domain.shipment.*;
import nl.codecentric.cqrs_warehouse.repositories.ShipmentDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ShipmentControllerTest {
    private static final String CUSTOMER_NAME = "Jumbo";

    @Mock
    CommandGateway commandGateway;

    @Mock
    QueryGateway queryGateway;

    @InjectMocks
    ShipmentController shipmentController;

    @Test
    public void createShipment() {
        CreateShipmentCommand command = CreateShipmentCommand.builder()
                .shipmentId(UUID.randomUUID())
                .articleId(UUID.randomUUID())
                .customerName(CUSTOMER_NAME)
                .build();

        shipmentController.createShipment(command);

        Mockito.verify(commandGateway).sendAndWait(command);
    }

    @Test
    public void initialiseShipment() {
        InitialiseShipmentCommand command = InitialiseShipmentCommand.builder()
                .shipmentId(UUID.randomUUID())
                .articleId(UUID.randomUUID())
                .customer(CUSTOMER_NAME)
                .volume(3)
                .build();

        shipmentController.initialiseShipment(command);

        Mockito.verify(commandGateway).sendAndWait(command);
    }

    @Test
    public void departShipment() {
        UUID shipmentId = UUID.randomUUID();

        DepartureShipmentCommand command = DepartureShipmentCommand.builder()
                .shipmentId(shipmentId)
                .build();

        shipmentController.departShipment(shipmentId.toString());

        Mockito.verify(commandGateway).sendAndWait(command);
    }

    @Test
    public void getShipments() {
        shipmentController.getShipments();
        Mockito.verify(queryGateway).query(new FetchAllShipmentsQuery(), ResponseTypes.multipleInstancesOf(ShipmentDTO.class));
    }

    @Test
    public void getShipment() {
        String shipmentId = UUID.randomUUID().toString();

        shipmentController.getShipment(shipmentId);
        Mockito.verify(queryGateway).query(new FetchShipmentByIdQuery(shipmentId), ResponseTypes.optionalInstanceOf(ShipmentDTO.class));
    }
}
