package nl.codecentric.cqrs_warehouse.projections;

import nl.codecentric.cqrs_warehouse.domain.article.ArticleCreatedEvent;
import nl.codecentric.cqrs_warehouse.domain.article.FetchAllArticlesQuery;
import nl.codecentric.cqrs_warehouse.repositories.ArticleDTO;
import nl.codecentric.cqrs_warehouse.repositories.ArticleRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ProcessingGroup("ArticleProjection")
public class ArticleProjection {

    @Autowired
    ArticleRepository articleRepository;

    @EventHandler
    public void on(ArticleCreatedEvent event) {
        articleRepository.save(new ArticleDTO(event.getId().toString(), event.getName()));
    }

    @QueryHandler
    public List<ArticleDTO> on(FetchAllArticlesQuery query) {
        return articleRepository.findAll();
    }
}
