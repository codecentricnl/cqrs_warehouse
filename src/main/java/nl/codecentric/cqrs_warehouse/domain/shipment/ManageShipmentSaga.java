package nl.codecentric.cqrs_warehouse.domain.shipment;

import lombok.extern.slf4j.Slf4j;
import nl.codecentric.cqrs_warehouse.domain.article.ArticleOutOfStockEvent;
import nl.codecentric.cqrs_warehouse.domain.container.ContainerClaimedEvent;
import nl.codecentric.cqrs_warehouse.domain.container.ContainerLoadedEvent;
import nl.codecentric.cqrs_warehouse.domain.container.ContainerUnclaimedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Saga
@Slf4j
public class ManageShipmentSaga {

    private String articleId;
    private Integer volume;
    private List<String> claimedContainers;
    private List<String> loadedContainers;
    private String shipmentId;

    @Autowired
    private transient CommandGateway commandGateway;

    public void on(ShipmentInitialisedEvent event) {
    }

    public void on(ShipmentCreatedEvent event) {
    }

    public void on(ContainerClaimedEvent event) {
    }

    public void on(ArticleOutOfStockEvent event) {
    }

    public void on(ContainerUnclaimedEvent event) {
    }

    public void on(ContainerLoadedEvent event) {
    }

    public void on(ShipmentDeparturedEvent event) {
    }

    public void on(ShipmentCanceledEvent event) {
    }
}
