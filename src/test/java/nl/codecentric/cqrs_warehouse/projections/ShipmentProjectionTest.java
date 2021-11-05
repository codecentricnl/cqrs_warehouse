package nl.codecentric.cqrs_warehouse.projections;

import nl.codecentric.cqrs_warehouse.domain.container.ContainerUnloadedEvent;
import nl.codecentric.cqrs_warehouse.domain.shipment.*;
import nl.codecentric.cqrs_warehouse.repositories.ShipmentDTO;
import nl.codecentric.cqrs_warehouse.repositories.ShipmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShipmentProjectionTest {

    private static final UUID SHIPMENT_ID = UUID.randomUUID();
    private static final UUID CONTAINER_ID = UUID.randomUUID();
    private static final UUID ARTICLE_ID = UUID.randomUUID();
    private static final String ARTICLE_NAME = "Danoontje";
    private static final String CUSTOMER_NAME = "Jumbo";
    private static final int VOLUME = 3;
    private static final String SHIPMENT_CREATED_STATE = "Ready to unload";
    private static final String SHIPMENT_CLAIMED_STATE = "Containers reserved, ready to load";
    private static final String SHIPMENT_RESOLVED_STATE = "Containers loaded, ready to be transported";
    private static final String SHIPMENT_CANCELED_STATE = "Failed! We do not have enough stock";
    private static final String CONTAINER_UNLOADED_LOCATION = "Docking station 1";

    @Captor
    private ArgumentCaptor<ShipmentDTO> shipmentDTOArgumentCaptor;

    @Mock
    private ShipmentRepository shipmentRepository;

    @InjectMocks
    private ShipmentProjection projection;

    @Test
    public void shipmentCreated() {
        projection.on(shipmentCreatedEvent());

        verify(shipmentRepository).save(shipmentDTO(VOLUME));
    }

    @Test
    public void containerUnloaded() {
        when(shipmentRepository.findById(SHIPMENT_ID.toString())).thenReturn(Optional.of(shipmentDTO(VOLUME)));

        projection.on(containerUnloadedEvent());

        verify(shipmentRepository).save(shipmentDTOArgumentCaptor.capture());
        ShipmentDTO shipmentDTO = shipmentDTOArgumentCaptor.getValue();
        assertThat(shipmentDTO.getVolume()).isEqualTo(2);
    }

    @Test
    public void deleteShipmentWhenAllContainerUnloaded() {
        when(shipmentRepository.findById(SHIPMENT_ID.toString())).thenReturn(Optional.of(shipmentDTO(1)));

        projection.on(containerUnloadedEvent());

        verify(shipmentRepository).deleteById(SHIPMENT_ID.toString());
    }

    @Test
    public void shipmentClaimed() {
        when(shipmentRepository.findById(SHIPMENT_ID.toString())).thenReturn(Optional.of(shipmentDTO(1)));

        projection.on(shipmentClaimedEvent());

        verify(shipmentRepository).save(shipmentDTOArgumentCaptor.capture());
        ShipmentDTO shipmentDTO = shipmentDTOArgumentCaptor.getValue();
        assertThat(shipmentDTO.getState()).isEqualTo(SHIPMENT_CLAIMED_STATE);
    }

    @Test
    public void shipmentResolved() {
        when(shipmentRepository.findById(SHIPMENT_ID.toString())).thenReturn(Optional.of(shipmentDTO(1)));

        projection.on(shipmentResolvedEvent());

        verify(shipmentRepository).save(shipmentDTOArgumentCaptor.capture());
        ShipmentDTO shipmentDTO = shipmentDTOArgumentCaptor.getValue();
        assertThat(shipmentDTO.getState()).isEqualTo(SHIPMENT_RESOLVED_STATE);
    }

    @Test
    public void shipmentCanceled() {
        when(shipmentRepository.findById(SHIPMENT_ID.toString())).thenReturn(Optional.of(shipmentDTO(1)));

        projection.on(shipmentCanceledEvent());

        verify(shipmentRepository).save(shipmentDTOArgumentCaptor.capture());
        ShipmentDTO shipmentDTO = shipmentDTOArgumentCaptor.getValue();
        assertThat(shipmentDTO.getState()).isEqualTo(SHIPMENT_CANCELED_STATE);
    }

    @Test
    public void shipmentDeparted() {
        projection.on(shipmentDepartedEvent());

        verify(shipmentRepository).deleteById(SHIPMENT_ID.toString());
    }

    @Test
    public void fetchAllShipments() {
        projection.on(new FetchAllShipmentsQuery());

        verify(shipmentRepository).findAll();
    }

    @Test
    public void fetchShipmentById() {
        projection.on(new FetchShipmentByIdQuery(SHIPMENT_ID.toString()));

        verify(shipmentRepository).findById(SHIPMENT_ID.toString());
    }

    private ShipmentDTO shipmentDTO(int volume) {
        return ShipmentDTO.builder()
                .shipmentId(SHIPMENT_ID.toString())
                .articleId(ARTICLE_ID.toString())
                .volume(volume)
                .customerName(CUSTOMER_NAME)
                .state(SHIPMENT_CREATED_STATE)
                .build();
    }

    private ShipmentCreatedEvent shipmentCreatedEvent() {
        return new ShipmentCreatedEvent(SHIPMENT_ID, CUSTOMER_NAME, VOLUME, ARTICLE_ID, SHIPMENT_CREATED_STATE);
    }

    private ContainerUnloadedEvent containerUnloadedEvent() {
        return new ContainerUnloadedEvent(CONTAINER_ID, SHIPMENT_ID, ARTICLE_ID, Instant.now().plus(10, ChronoUnit.DAYS), ARTICLE_NAME, CONTAINER_UNLOADED_LOCATION);
    }

    private ShipmentClaimedEvent shipmentClaimedEvent() {
        return new ShipmentClaimedEvent(SHIPMENT_ID, SHIPMENT_CLAIMED_STATE);
    }

    private ShipmentResolvedEvent shipmentResolvedEvent() {
        return new ShipmentResolvedEvent(SHIPMENT_ID, SHIPMENT_RESOLVED_STATE);
    }

    private ShipmentCanceledEvent shipmentCanceledEvent() {
        return new ShipmentCanceledEvent(SHIPMENT_ID, SHIPMENT_CANCELED_STATE);
    }

    private ShipmentDeparturedEvent shipmentDepartedEvent() {
        return new ShipmentDeparturedEvent(SHIPMENT_ID);
    }
}
