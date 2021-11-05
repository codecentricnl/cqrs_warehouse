package nl.codecentric.cqrs_warehouse.controllers;

import lombok.extern.slf4j.Slf4j;
import nl.codecentric.cqrs_warehouse.domain.article.CreateArticleCommand;
import nl.codecentric.cqrs_warehouse.domain.article.FetchAllArticlesQuery;
import nl.codecentric.cqrs_warehouse.domain.container.*;
import nl.codecentric.cqrs_warehouse.repositories.ArticleDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
public class ArticleController {

    @Autowired
    CommandGateway commandGateway;

    @Autowired
    QueryGateway queryGateway;

    @PostMapping(path = "/articles/create")
    public UUID createArticle(@RequestBody CreateArticleCommand command) {
        commandGateway.sendAndWait(command);
        return command.getId();
    }

    @GetMapping(path = "/articles")
    public CompletableFuture<List<ArticleDTO>> getArticles() {
        return queryGateway.query(new FetchAllArticlesQuery(), ResponseTypes.multipleInstancesOf(ArticleDTO.class));
    }

    @PostMapping(path = "/articles/unload-container")
    public UUID unloadContainer(@RequestBody UnloadContainerCommand command) {
        commandGateway.sendAndWait(command);
        return command.getContainerId();
    }

    @PostMapping(path = "/articles/load-container")
    public UUID loadContainer(@RequestBody LoadContainerCommand command) {
        commandGateway.sendAndWait(command);
        return command.getContainerId();
    }

    @PostMapping(path = "/articles/move-container")
    public UUID moveContainer(@RequestBody MoveContainerCommand command) {
        commandGateway.sendAndWait(command);
        return command.getContainerId();
    }
}
