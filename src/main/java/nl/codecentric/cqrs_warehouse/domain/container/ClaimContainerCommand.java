package nl.codecentric.cqrs_warehouse.domain.container;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class ClaimContainerCommand {
    UUID articleId;
    UUID shipmentId;
}
