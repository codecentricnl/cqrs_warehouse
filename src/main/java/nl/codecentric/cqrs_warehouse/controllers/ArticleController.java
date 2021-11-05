package nl.codecentric.cqrs_warehouse.controllers;

import lombok.extern.slf4j.Slf4j;
import nl.codecentric.cqrs_warehouse.domain.article.CreateArticleCommand;
import nl.codecentric.cqrs_warehouse.domain.container.LoadContainerCommand;
import nl.codecentric.cqrs_warehouse.domain.container.MoveContainerCommand;
import nl.codecentric.cqrs_warehouse.domain.container.UnloadContainerCommand;
import nl.codecentric.cqrs_warehouse.repositories.ArticleDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
        return null;
    }

    @GetMapping(path = "/articles")
    public CompletableFuture<List<ArticleDTO>> getArticles() {
        return null;
    }

    @PostMapping(path = "/articles/unload-container")
    public UUID unloadContainer(@RequestBody UnloadContainerCommand command) {
        return null;
    }

    @PostMapping(path = "/articles/load-container")
    public UUID loadContainer(@RequestBody LoadContainerCommand command) {
        return null;
    }

    @PostMapping(path = "/articles/move-container")
    public UUID moveContainer(@RequestBody MoveContainerCommand command) {
        return null;
    }
}
