package nl.codecentric.cqrs_warehouse.domain;

import nl.codecentric.cqrs_warehouse.domain.article.ArticleAggregate;
import nl.codecentric.cqrs_warehouse.domain.article.ArticleCreatedEvent;
import nl.codecentric.cqrs_warehouse.domain.article.ArticleOutOfStockEvent;
import nl.codecentric.cqrs_warehouse.domain.article.CreateArticleCommand;
import nl.codecentric.cqrs_warehouse.domain.container.*;
import nl.codecentric.cqrs_warehouse.domain.shipment.*;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class ShipmentAggregateTest {

    private static final UUID ARTICLE_ID = UUID.randomUUID();
    private static final UUID SHIPMENT_ID = UUID.randomUUID();
    private static final String CUSTOMER_NAME = "Jumbo";
    private static final String CREATE_SHIPMENT_STATE = "Ready to unload";
    private static final String SHIPMENT_CLAIMED_STATE = "Containers reserved, ready to load";
    private static final String SHIPMENT_RESOLVED_STATE = "Containers loaded, ready to be transported";
    private static final String SHIPMENT_CANCELED_STATE = "Failed! We do not have enough stock";
    private static final Integer VOLUME = 3;

    private FixtureConfiguration<ShipmentAggregate> fixture;

    @BeforeEach
    public void setup() {
        fixture = new AggregateTestFixture<>(ShipmentAggregate.class);
    }

    @Test
    public void createShipmentAggregate() {
        fixture.givenNoPriorActivity()
                .when(CreateShipmentCommand.builder()
                        .shipmentId(SHIPMENT_ID)
                        .articleId(ARTICLE_ID)
                        .customerName(CUSTOMER_NAME)
                        .volume(VOLUME)
                        .build())
                .expectSuccessfulHandlerExecution()
                .expectEvents(shipmentCreatedEvent());
    }

    @Test
    public void claimShipment() {
        fixture.given(shipmentCreatedEvent())
                .when(new ClaimShipmentCommand(SHIPMENT_ID, SHIPMENT_CLAIMED_STATE))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new ShipmentClaimedEvent(SHIPMENT_ID, SHIPMENT_CLAIMED_STATE));
    }

    @Test
    public void resolveShipment() {
        fixture.given(shipmentCreatedEvent())
                .when(new ResolveShipmentCommand(SHIPMENT_ID, SHIPMENT_RESOLVED_STATE))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new ShipmentResolvedEvent(SHIPMENT_ID, SHIPMENT_RESOLVED_STATE));
    }

    @Test
    public void departShipment() {
        fixture.given(shipmentCreatedEvent())
                .when(new DepartureShipmentCommand(SHIPMENT_ID))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new ShipmentDeparturedEvent(SHIPMENT_ID));
    }

    @Test
    public void cancelShipment() {
        fixture.given(shipmentCreatedEvent())
                .when(new CancelShipmentCommand(SHIPMENT_ID, SHIPMENT_CANCELED_STATE))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new ShipmentCanceledEvent(SHIPMENT_ID, SHIPMENT_CANCELED_STATE));
    }

    private ShipmentCreatedEvent shipmentCreatedEvent() {
        return new ShipmentCreatedEvent(SHIPMENT_ID, CUSTOMER_NAME, VOLUME, ARTICLE_ID, CREATE_SHIPMENT_STATE);
    }
}
