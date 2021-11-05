package nl.codecentric.cqrs_warehouse.domain.container;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Container {
    
    private UUID containerId;
    private Instant expirationDate;
    private Boolean isReserved;
    private String reservedFor;
    private String location;

    private void handle(MoveContainerCommand command) {
    }

    private void on(ContainerMovedEvent event) {
    }

    private void on(ContainerClaimedEvent event) {
    }

    private void handle(UnclaimContainerCommand command) {
    }

    private void on(ContainerUnclaimedEvent event) {
    }

}
