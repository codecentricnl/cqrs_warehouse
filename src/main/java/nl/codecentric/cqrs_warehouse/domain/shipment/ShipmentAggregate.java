package nl.codecentric.cqrs_warehouse.domain.shipment;

import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Aggregate
@NoArgsConstructor
public class ShipmentAggregate {
    @AggregateIdentifier
    private UUID shipmentId;
    private String customerName;
    private Integer volume;
    private UUID articleId;
    private String state;

    @CommandHandler
    public ShipmentAggregate(CreateShipmentCommand command) {
        AggregateLifecycle.apply(new ShipmentCreatedEvent(
                command.getShipmentId(),
                command.getCustomerName(),
                command.getVolume(),
                command.getArticleId(),
                "Ready to unload"
        ));
    }

    @EventSourcingHandler
    public void on(ShipmentCreatedEvent event) {
        this.shipmentId = event.getShipmentId();
        this.customerName = event.getCustomerName();
        this.volume = event.getVolume();
        this.articleId = event.getArticleId();
        this.state = event.getState();
    }

    @CommandHandler
    public void handle(ClaimShipmentCommand command) {
        AggregateLifecycle.apply(new ShipmentClaimedEvent(command.getShipmentId(), command.getState()));
    }

    @EventSourcingHandler
    public void on(ShipmentClaimedEvent event) {
        this.state = event.getState();
    }

    @CommandHandler
    public void handle(ResolveShipmentCommand command) {
        AggregateLifecycle.apply(new ShipmentResolvedEvent(command.getShipmentId(), command.getState()));
    }

    @EventSourcingHandler
    public void on(ShipmentResolvedEvent event) {
        this.state = event.getState();
    }


    @CommandHandler
    public void on(DepartureShipmentCommand command) {
        AggregateLifecycle.apply(new ShipmentDeparturedEvent(command.getShipmentId()));
    }

    @CommandHandler
    public void on(CancelShipmentCommand command) {
        AggregateLifecycle.apply(new ShipmentCanceledEvent(command.getShipmentId(), command.getState()));
    }


}
