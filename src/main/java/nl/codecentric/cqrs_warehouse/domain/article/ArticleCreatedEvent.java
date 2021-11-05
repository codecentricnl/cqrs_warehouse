package nl.codecentric.cqrs_warehouse.domain.article;

import java.util.UUID;

import lombok.Data;

@Data
public class ArticleCreatedEvent {
    private final UUID id;
    private final String name;
}
