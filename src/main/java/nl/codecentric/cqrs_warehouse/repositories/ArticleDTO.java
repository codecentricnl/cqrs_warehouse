package nl.codecentric.cqrs_warehouse.repositories;

import lombok.Value;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
@Value
public class ArticleDTO {
    @MongoId
    String articleId;
    String name;
}
