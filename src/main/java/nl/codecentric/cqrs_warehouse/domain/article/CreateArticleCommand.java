package nl.codecentric.cqrs_warehouse.domain.article;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CreateArticleCommand {
    private UUID id;
    private String name;
}
