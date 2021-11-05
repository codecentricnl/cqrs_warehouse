package nl.codecentric.cqrs_warehouse.domain.container;

import lombok.Data;

import java.util.UUID;

@Data
public class ExpireContainerCommand {
    private final UUID articleId;
    private final UUID containerId;
}


