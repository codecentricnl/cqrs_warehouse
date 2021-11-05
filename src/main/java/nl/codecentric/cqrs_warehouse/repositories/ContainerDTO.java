package nl.codecentric.cqrs_warehouse.repositories;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;

@Document
@Value
@Builder(toBuilder = true)
public class ContainerDTO {
    @MongoId
    String containerId;
    String articleId;
    String articleName;
    Instant expirationDate;
    boolean isReserved;
    String reservedFor;
    String location;
}
