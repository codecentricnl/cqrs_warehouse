package nl.codecentric.cqrs_warehouse.controllers;

import nl.codecentric.cqrs_warehouse.domain.article.CreateArticleCommand;
import nl.codecentric.cqrs_warehouse.domain.article.FetchAllArticlesQuery;
import nl.codecentric.cqrs_warehouse.domain.container.LoadContainerCommand;
import nl.codecentric.cqrs_warehouse.domain.container.MoveContainerCommand;
import nl.codecentric.cqrs_warehouse.domain.container.UnloadContainerCommand;
import nl.codecentric.cqrs_warehouse.repositories.ArticleDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ArticleControllerTest {

    private static final String ARTICLE_NAME = "Danoontje";

    @Mock
    CommandGateway commandGateway;

    @Mock
    QueryGateway queryGateway;

    @InjectMocks
    ArticleController articleController;

    @Test
    public void createArticle() {
        CreateArticleCommand command = CreateArticleCommand.builder()
                .id(UUID.randomUUID())
                .name(ARTICLE_NAME)
                .build();

        articleController.createArticle(command);

        Mockito.verify(commandGateway).sendAndWait(command);
    }

    @Test
    public void unloadContainer() {
        UnloadContainerCommand command = UnloadContainerCommand.builder()
                .articleId(UUID.randomUUID())
                .containerId(UUID.randomUUID())
                .shipmentId(UUID.randomUUID())
                .location("Unloading dock")
                .expirationDate(Instant.now())
                .build();

        articleController.unloadContainer(command);

        Mockito.verify(commandGateway).sendAndWait(command);
    }

    @Test
    public void loadContainer() {
        LoadContainerCommand command = LoadContainerCommand.builder()
                .articleId(UUID.randomUUID())
                .containerId(UUID.randomUUID())
                .shipmentId(UUID.randomUUID())
                .build();

        articleController.loadContainer(command);

        Mockito.verify(commandGateway).sendAndWait(command);
    }

    @Test
    public void moveContainer() {
        MoveContainerCommand command = MoveContainerCommand.builder()
                .articleId(UUID.randomUUID())
                .containerId(UUID.randomUUID())
                .location("A-25/72")
                .build();

        articleController.moveContainer(command);

        Mockito.verify(commandGateway).sendAndWait(command);
    }

    @Test
    public void getArticles() {
        articleController.getArticles();
        Mockito.verify(queryGateway).query(new FetchAllArticlesQuery(), ResponseTypes.multipleInstancesOf(ArticleDTO.class));
    }
}
