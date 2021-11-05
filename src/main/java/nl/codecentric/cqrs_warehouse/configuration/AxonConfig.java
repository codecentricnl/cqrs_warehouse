package nl.codecentric.cqrs_warehouse.configuration;

import com.mongodb.client.MongoClient;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.eventhandling.saga.repository.MongoSagaStore;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.extensions.mongo.eventsourcing.tokenstore.MongoTokenStore;
import org.axonframework.modelling.saga.repository.SagaStore;
import org.axonframework.serialization.Serializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

    @Bean
    public DefaultMongoTemplate defaultMongoTemplate(MongoClient mongoClient) {
        return DefaultMongoTemplate.builder()
                .mongoDatabase(mongoClient)
                .build();
    }

    @Bean
    public TokenStore tokenStore(DefaultMongoTemplate defaultMongoTemplate, Serializer defaultSerializer) {
        return MongoTokenStore.builder()
                .mongoTemplate(defaultMongoTemplate)
                .serializer(defaultSerializer)
                .build();
    }

    @Bean
    public SagaStore sagaStore(DefaultMongoTemplate defaultMongoTemplate, Serializer defaultSerializer) {
        return MongoSagaStore.builder()
                .mongoTemplate(defaultMongoTemplate)
                .serializer(defaultSerializer)
                .build();
    }

    @Bean
    public EmbeddedEventStore eventStore(EventStorageEngine storageEngine) {
        return EmbeddedEventStore.builder()
                .storageEngine(storageEngine)
                .build();
    }

    @Bean
    public EventStorageEngine storageEngine(MongoClient client) {
        return MongoEventStorageEngine.builder().mongoTemplate(DefaultMongoTemplate.builder().mongoDatabase(client).build()).build();
    }
}
