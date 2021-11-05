package nl.codecentric.cqrs_warehouse.projections;

import nl.codecentric.cqrs_warehouse.domain.article.ArticleCreatedEvent;
import nl.codecentric.cqrs_warehouse.domain.article.FetchAllArticlesQuery;
import nl.codecentric.cqrs_warehouse.repositories.ArticleDTO;
import nl.codecentric.cqrs_warehouse.repositories.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ArticleProjectionTest {
    private static final UUID ARTICLE_ID = UUID.randomUUID();
    private static final String ARTICLE_NAME = "Danoontje";

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleProjection projection;

    @Test
    public void articleCreated() {
        projection.on(articleCreatedEvent());

        verify(articleRepository).save(new ArticleDTO(ARTICLE_ID.toString(), ARTICLE_NAME));
    }

    @Test
    public void fetchAllArticles() {
        projection.on(new FetchAllArticlesQuery());

        verify(articleRepository).findAll();
    }

    private ArticleCreatedEvent articleCreatedEvent() {
        return new ArticleCreatedEvent(ARTICLE_ID, ARTICLE_NAME);
    }
}
