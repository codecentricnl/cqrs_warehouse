package nl.codecentric.cqrs_warehouse.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends MongoRepository<ShipmentDTO, String> {
}
