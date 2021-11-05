package nl.codecentric.cqrs_warehouse.domain.shipment;

import java.util.UUID;

import lombok.Builder;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InitialiseShipmentCommand {
    @TargetAggregateIdentifier
    private UUID articleId;
    private UUID shipmentId;
    private String customer;
    private Integer volume;
}
