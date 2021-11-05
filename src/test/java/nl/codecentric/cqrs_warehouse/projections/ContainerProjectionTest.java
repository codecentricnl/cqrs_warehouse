package nl.codecentric.cqrs_warehouse.projections;

import nl.codecentric.cqrs_warehouse.domain.container.*;
import nl.codecentric.cqrs_warehouse.domain.shipment.ShipmentDeparturedEvent;
import nl.codecentric.cqrs_warehouse.repositories.ContainerDTO;
import nl.codecentric.cqrs_warehouse.repositories.ContainerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContainerProjectionTest {
    private static final UUID CONTAINER_ID = UUID.randomUUID();
    private static final UUID ARTICLE_ID = UUID.randomUUID();
    private static final UUID SHIPMENT_ID = UUID.randomUUID();
    private static final String ARTICLE_NAME = "Danoontje";
    private static final String CONTAINER_UNLOADED_LOCATION = "Docking bay 1";
    private static final String CONTAINER_MOVED_LOCATION = "Cell A 75/52";
    private static final Instant EXPIRATION_DATE = Instant.now().plus(10, ChronoUnit.DAYS);

    @Captor
    private ArgumentCaptor<ContainerDTO> containerDTOArgumentCaptor;

    @Mock
    private ContainerRepository containerRepository;

    @InjectMocks
    private ContainerProjection projection;

    @Test
    public void containerUnloaded() {
        projection.on(containerUnloadedEvent());

        verify(containerRepository).save(containerDTO());
    }

    @Test
    public void containerMoved() {
        when(containerRepository.findById(CONTAINER_ID.toString())).thenReturn(Optional.of(containerDTO()));

        projection.on(containerMovedEvent());

        verify(containerRepository).save(containerDTOArgumentCaptor.capture());
        ContainerDTO containerDTO = containerDTOArgumentCaptor.getValue();

        assertThat(containerDTO.getLocation()).isEqualTo(CONTAINER_MOVED_LOCATION);
    }

    @Test
    public void containerLoaded() {
        when(containerRepository.findById(CONTAINER_ID.toString())).thenReturn(Optional.of(containerDTO()));

        projection.on(containerLoadedEvent());

        verify(containerRepository).save(containerDTOArgumentCaptor.capture());
        ContainerDTO containerDTO = containerDTOArgumentCaptor.getValue();

        assertThat(containerDTO.getLocation()).isEqualTo("In truck for shipment: " + SHIPMENT_ID.toString());
    }

    @Test
    public void containerClaimed() {
        when(containerRepository.findById(CONTAINER_ID.toString())).thenReturn(Optional.of(containerDTO()));

        projection.on(containerClaimedEvent());

        verify(containerRepository).save(containerDTOArgumentCaptor.capture());
        ContainerDTO containerDTO = containerDTOArgumentCaptor.getValue();

        assertThat(containerDTO.isReserved()).isTrue();
        assertThat(containerDTO.getReservedFor()).isEqualTo(SHIPMENT_ID.toString());
    }

    @Test
    public void containerUnclaimed() {
        when(containerRepository.findById(CONTAINER_ID.toString())).thenReturn(Optional.of(containerDTO()));

        projection.on(containerUnclaimedEvent());

        verify(containerRepository).save(containerDTOArgumentCaptor.capture());
        ContainerDTO containerDTO = containerDTOArgumentCaptor.getValue();

        assertThat(containerDTO.isReserved()).isFalse();
        assertThat(containerDTO.getReservedFor()).isEmpty();
    }

    @Test
    public void containerExpired() {
        projection.on(containerExpiredEvent());

        verify(containerRepository).deleteById(CONTAINER_ID.toString());
    }

    @Test
    public void shipmentDeparted() {
        when(containerRepository.findByReservedFor(SHIPMENT_ID.toString())).thenReturn(Collections.singletonList(containerDTO()));

        projection.on(shipmentDepartedEvent());

        verify(containerRepository).deleteById(CONTAINER_ID.toString());
    }

    @Test
    public void fetchAllContainers() {
        projection.on(new FetchAllContainersQuery());

        verify(containerRepository).findAll();
    }

    @Test
    public void fetchContainerById() {
        projection.on(new FetchContainerByIdQuery(CONTAINER_ID.toString()));

        verify(containerRepository).findById(CONTAINER_ID.toString());
    }

    private ContainerDTO containerDTO() {
        return ContainerDTO.builder()
                .containerId(CONTAINER_ID.toString())
                .articleId(ARTICLE_ID.toString())
                .isReserved(false)
                .reservedFor("")
                .location(CONTAINER_UNLOADED_LOCATION)
                .articleName(ARTICLE_NAME)
                .expirationDate(EXPIRATION_DATE)
                .build();
    }

    private ContainerUnloadedEvent containerUnloadedEvent() {
        return new ContainerUnloadedEvent(CONTAINER_ID, SHIPMENT_ID, ARTICLE_ID, EXPIRATION_DATE, ARTICLE_NAME, CONTAINER_UNLOADED_LOCATION);
    }

    private ContainerMovedEvent containerMovedEvent() {
        return new ContainerMovedEvent(CONTAINER_ID, CONTAINER_MOVED_LOCATION);
    }

    private ContainerLoadedEvent containerLoadedEvent() {
        return new ContainerLoadedEvent(CONTAINER_ID, SHIPMENT_ID);
    }

    private ContainerClaimedEvent containerClaimedEvent() {
        return new ContainerClaimedEvent(ARTICLE_ID, CONTAINER_ID, SHIPMENT_ID);
    }

    private ContainerUnclaimedEvent containerUnclaimedEvent() {
        return new ContainerUnclaimedEvent(ARTICLE_ID, CONTAINER_ID, SHIPMENT_ID);
    }

    private ContainerExpiredEvent containerExpiredEvent() {
        return new ContainerExpiredEvent(CONTAINER_ID);
    }

    private ShipmentDeparturedEvent shipmentDepartedEvent() {
        return new ShipmentDeparturedEvent(SHIPMENT_ID);
    }
}
