package nl.codecentric.cqrs_warehouse.repositories;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
@Value
@Builder(toBuilder = true)
public class ShipmentDTO {
    @MongoId
    String shipmentId;
    String customerName;
    Integer volume;
    String articleId;
    String state;
}
