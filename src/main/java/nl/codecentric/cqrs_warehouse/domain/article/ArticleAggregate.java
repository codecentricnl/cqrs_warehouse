package nl.codecentric.cqrs_warehouse.domain.article;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.codecentric.cqrs_warehouse.domain.container.*;
import nl.codecentric.cqrs_warehouse.domain.shipment.InitialiseShipmentCommand;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@Slf4j
public class ArticleAggregate {
    private UUID id;
    private String name;

    private Map<UUID, Container> containers;

    public ArticleAggregate(CreateArticleCommand command) {
    }

    private void on(ArticleCreatedEvent event) {
    }

    private void handle(UnloadContainerCommand command) {
    }

    private void on(ContainerUnloadedEvent event) {
    }

    public void handle(InitialiseShipmentCommand command) {
    }

    private void handle(LoadContainerCommand command) throws IllegalStateException {
    }

    private void handle(ContainerLoadedEvent event) {
    }

    private void handle(ExpireContainerCommand command) throws IllegalStateException {
    }

    private void handle(ContainerExpiredEvent event) {
    }

    private void handle(ClaimContainerCommand command) {
    }

    private UUID findOldestAvailableContainer() {
        return this.containers.values().stream()
                .filter(container -> !container.getIsReserved())
                .min(Comparator.comparingLong(container -> container.getExpirationDate().getEpochSecond())).get().getContainerId();
    }

    private boolean hasAvailableContainers() {
        return !this.containers.isEmpty() &&
                this.containers.values().stream()
                        .anyMatch(container -> !container.getIsReserved() &&
                                container.getExpirationDate().isAfter(Instant.now().plus(5, ChronoUnit.DAYS)));
    }
}
