package nl.codecentric.cqrs_warehouse.controllers;

import nl.codecentric.cqrs_warehouse.domain.container.*;
import nl.codecentric.cqrs_warehouse.repositories.ContainerDTO;
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
public class ContainerControllerTest {

    @Mock
    QueryGateway queryGateway;

    @InjectMocks
    ContainerController containerController;

    @Test
    public void getContainers() {
        containerController.getAllContainers();
        Mockito.verify(queryGateway).query(new FetchAllContainersQuery(), ResponseTypes.multipleInstancesOf(ContainerDTO.class));
    }

    @Test
    public void getContainer() {
        String containerId = UUID.randomUUID().toString();

        containerController.getContainer(containerId);
        Mockito.verify(queryGateway).query(new FetchContainerByIdQuery(containerId), ResponseTypes.optionalInstanceOf(ContainerDTO.class));
    }
}
