package nl.codecentric.cqrs_warehouse.domain.article;

import java.util.UUID;

import lombok.Data;

@Data
public class ArticleOutOfStockEvent {
    private final UUID articleId;
    private final UUID shipmentId;
}
