package nl.codecentric.cqrs_warehouse.domain.container;

import lombok.Data;

@Data
public class FetchContainerByIdQuery {
    final String containerId;
}
