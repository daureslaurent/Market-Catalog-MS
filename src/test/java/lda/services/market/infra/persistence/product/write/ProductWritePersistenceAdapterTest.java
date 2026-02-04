package lda.services.market.infra.persistence.product.write;

import lda.services.market.domain.product.ProductSampleTest;
import lda.services.market.infra.persistence.write.product.ProductWritePersistenceAdapter;
import lda.services.market.infra.persistence.write.product.mapper.ProductWritePersistenceMapper;
import lda.services.market.infra.persistence.write.product.repository.ProductWriteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductWritePersistenceAdapterTest {

    @InjectMocks
    private ProductWritePersistenceAdapter productPersistenceAdapter;

    @Mock
    private ProductWriteRepository productWriteRepository;

    @Mock
    private ProductWritePersistenceMapper productWritePersistenceMapper;


    @Test
    void getById_whenNotFound_then_empty() {

        // Given
        when(productWriteRepository.findById(any()))
                .thenReturn(Optional.empty());

        // When
        final var product = productPersistenceAdapter.getById(UUID.randomUUID());

        // Then
        assertThat(product).isNotNull();
        assertThat(product).isEmpty();

        verify(productWriteRepository).findById(any());
        verify(productWritePersistenceMapper, never()).toDomain(any());
    }

    @Test
    void getById_when_nominal() {
        final var productEntity = ProductSampleTest.entityWrite();
        final var productDomain = ProductSampleTest.domain();

        // Given
        when(productWriteRepository.findById(productEntity.getId()))
                .thenReturn(Optional.of(productEntity));
        when(productWritePersistenceMapper.toDomain(productEntity))
                .thenReturn(productDomain);

        // When
        final var productReturned = productPersistenceAdapter.getById(productEntity.getId());

        // Then
        assertThat(productReturned).isNotNull();
        assertThat(productReturned).isPresent();
        assertThat(productReturned).contains(productDomain);

        verify(productWriteRepository).findById(productEntity.getId());
        verify(productWritePersistenceMapper).toDomain(productEntity);
    }

    @Test
    void save_when_nominal() {
        final var productDomain = ProductSampleTest.domain()
                .toBuilder()
                .id(null)
                .build();
        final var productEntity = ProductSampleTest.entityWrite();
        productEntity.setId(null);

        // Given
        when(productWritePersistenceMapper.toEntity(productDomain))
                .thenReturn(productEntity);
        when(productWritePersistenceMapper.toDomain(productEntity))
                .thenReturn(productDomain);
        when(productWriteRepository.save(productEntity))
                .thenReturn(productEntity);

        // When
        final var productSaved = productPersistenceAdapter.save(productDomain);

        // Then
        assertThat(productSaved).isNotNull();
        assertThat(productSaved).isEqualTo(productDomain);

        verify(productWritePersistenceMapper).toEntity(productDomain);
        verify(productWritePersistenceMapper).toDomain(productEntity);
        verify(productWriteRepository).save(productEntity);
    }

}