package nl.codecentric.cqrs_warehouse.domain;

import nl.codecentric.cqrs_warehouse.domain.article.ArticleAggregate;
import nl.codecentric.cqrs_warehouse.domain.article.ArticleCreatedEvent;
import nl.codecentric.cqrs_warehouse.domain.article.ArticleOutOfStockEvent;
import nl.codecentric.cqrs_warehouse.domain.article.CreateArticleCommand;
import nl.codecentric.cqrs_warehouse.domain.container.*;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class ArticleAggregateTest {

    public static final UUID ARTICLE_ID = UUID.randomUUID();
    public static final String ARTICLE_NAME = "Danoontje";
    public static final UUID CONTAINER_ID = UUID.randomUUID();
    public static final UUID SHIPMENT_ID = UUID.randomUUID();
    public static final String UNLOADING_LOCATION = "Unloading dock";
    public static final String MOVE_LOCATION = "A-52/75";

    private FixtureConfiguration<ArticleAggregate> fixture;

    @BeforeEach
    public void setup() {
        fixture = new AggregateTestFixture<>(ArticleAggregate.class);
    }

    @Test
    public void createArticleAggregate() {
        fixture.givenNoPriorActivity()
                .when(CreateArticleCommand.builder()
                        .id(ARTICLE_ID)
                        .name(ARTICLE_NAME)
                        .build())
                .expectSuccessfulHandlerExecution()
                .expectEvents(new ArticleCreatedEvent(ARTICLE_ID, ARTICLE_NAME));
    }

    @Test
    public void unloadContainer() {
        Instant expirationDate = Instant.now();

        fixture.given(articleCreatedEvent())
                .when(UnloadContainerCommand.builder()
                        .articleId(ARTICLE_ID)
                        .containerId(CONTAINER_ID)
                        .shipmentId(SHIPMENT_ID)
                        .expirationDate(expirationDate)
                        .location(UNLOADING_LOCATION)
                        .build())
                .expectSuccessfulHandlerExecution()
                .expectEvents(containerUnloadedEvent(expirationDate));
    }

    @Test
    public void moveContainer() {
        Instant expirationDate = Instant.now();
        fixture.given(articleCreatedEvent(), containerUnloadedEvent(expirationDate))
                .when(MoveContainerCommand.builder()
                        .articleId(ARTICLE_ID)
                        .containerId(CONTAINER_ID)
                        .location(MOVE_LOCATION)
                .build())
                .expectSuccessfulHandlerExecution()
                .expectEvents(new ContainerMovedEvent(CONTAINER_ID, MOVE_LOCATION));
    }

    @Test
    public void claimContainer() {
        Instant expirationDate = Instant.now().plus(10L, ChronoUnit.DAYS);
        fixture.given(articleCreatedEvent(), containerUnloadedEvent(expirationDate))
                .when(ClaimContainerCommand.builder()
                        .articleId(ARTICLE_ID)
                        .shipmentId(SHIPMENT_ID)
                        .build())
                .expectSuccessfulHandlerExecution()
                .expectEvents(new ContainerClaimedEvent(ARTICLE_ID, CONTAINER_ID, SHIPMENT_ID));
    }

    @Test
    public void claimContainerFailsWhenNoContainersAvailable() {
        fixture.given(articleCreatedEvent())
                .when(ClaimContainerCommand.builder()
                        .articleId(ARTICLE_ID)
                        .shipmentId(SHIPMENT_ID)
                        .build())
                .expectSuccessfulHandlerExecution()
                .expectEvents(new ArticleOutOfStockEvent(ARTICLE_ID, SHIPMENT_ID));
    }

    @Test
    public void claimContainerFailsWhenContainerIsAlreadyClaimed() {
        Instant expirationDate = Instant.now().plus(10L, ChronoUnit.DAYS);
        fixture.given(articleCreatedEvent(), containerUnloadedEvent(expirationDate), containerClaimedEvent())
                .when(ClaimContainerCommand.builder()
                        .articleId(ARTICLE_ID)
                        .shipmentId(SHIPMENT_ID)
                        .build())
                .expectSuccessfulHandlerExecution()
                .expectEvents(new ArticleOutOfStockEvent(ARTICLE_ID, SHIPMENT_ID));
    }

    @Test
    public void claimContainerFailsBecauseNoAvailableContainersWithExpirationDateAfterTodayPlus5Days() {
        Instant now = Instant.now();
        fixture.given(articleCreatedEvent(), containerUnloadedEvent(now))
                .when(ClaimContainerCommand.builder()
                        .articleId(ARTICLE_ID)
                        .shipmentId(SHIPMENT_ID)
                .build())
                .expectSuccessfulHandlerExecution()
                .expectEvents(new ArticleOutOfStockEvent(ARTICLE_ID, SHIPMENT_ID));
    }

    @Test
    public void unclaimContainer() {
        Instant expirationDate = Instant.now().plus(10L, ChronoUnit.DAYS);
        fixture.given(articleCreatedEvent(), containerUnloadedEvent(expirationDate), containerClaimedEvent())
                .when(UnclaimContainerCommand.builder()
                        .articleId(ARTICLE_ID)
                        .shipmentId(SHIPMENT_ID)
                        .containerId(CONTAINER_ID)
                        .build())
                .expectSuccessfulHandlerExecution()
                .expectEvents(new ContainerUnclaimedEvent(ARTICLE_ID, CONTAINER_ID, SHIPMENT_ID));
    }

    @Test
    public void unclaimContainerFailsWhenContainerIsNotClaimed() {
        Instant expirationDate = Instant.now().plus(10L, ChronoUnit.DAYS);
        fixture.given(articleCreatedEvent(), containerUnloadedEvent(expirationDate))
                .when(UnclaimContainerCommand.builder()
                        .articleId(ARTICLE_ID)
                        .shipmentId(SHIPMENT_ID)
                        .containerId(CONTAINER_ID)
                        .build())
                .expectSuccessfulHandlerExecution()
                .expectNoEvents();
    }

    private ArticleCreatedEvent articleCreatedEvent() {
        return new ArticleCreatedEvent(ARTICLE_ID, ARTICLE_NAME);
    }

    private ContainerUnloadedEvent containerUnloadedEvent(Instant expirationDate) {
        return new ContainerUnloadedEvent(CONTAINER_ID, SHIPMENT_ID, ARTICLE_ID, expirationDate, ARTICLE_NAME, UNLOADING_LOCATION);
    }

    private ContainerClaimedEvent containerClaimedEvent() {
        return new ContainerClaimedEvent(ARTICLE_ID, CONTAINER_ID, SHIPMENT_ID);
    }

}
