package nl.codecentric.cqrs_warehouse.domain.container;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class UnloadContainerCommand {
    private UUID articleId;
    private UUID shipmentId;
    private UUID containerId;
    private String location;
    private Instant expirationDate;
}
