package nl.codecentric.cqrs_warehouse.domain.container;

import java.time.Instant;
import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.EntityId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Container {
    
    @EntityId
    private UUID containerId;
    private Instant expirationDate;
    private Boolean isReserved;
    private String reservedFor;
    private String location;

    @CommandHandler
    private void handle(MoveContainerCommand command) {
        if (command.getLocation().equals(this.location)) {
            throw new IllegalStateException("Cannot move to the same location.");
        }
        
        AggregateLifecycle.apply(new ContainerMovedEvent(
            command.getContainerId(),
            command.getLocation()
        ));
    }

    @EventSourcingHandler
    private void on(ContainerMovedEvent event) {
        this.location = event.getLocation();
    }

    @EventSourcingHandler
    private void on(ContainerClaimedEvent event) {
        this.setIsReserved(true);
        this.setReservedFor(event.getShipmentId().toString());
    }

    @CommandHandler
    private void handle(UnclaimContainerCommand command) {
        if (isReserved) {
            AggregateLifecycle.apply(new ContainerUnclaimedEvent(command.getArticleId(), command.getContainerId(), command.getShipmentId()));
        }
    }

    @EventSourcingHandler
    private void on(ContainerUnclaimedEvent event) {
        this.setIsReserved(false);
        this.setReservedFor("");
    }

}
