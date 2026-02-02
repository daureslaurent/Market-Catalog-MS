package lda.services.market.infra.persistence.product;

import lda.services.market.domain.product.ProductSampleTest;
import lda.services.market.infra.persistence.product.mapper.ProductPersistenceMapper;
import lda.services.market.infra.persistence.product.repository.ProductRepository;
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
class ProductPersistenceAdapterTest {

    @InjectMocks
    private ProductPersistenceAdapter productPersistenceAdapter;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductPersistenceMapper productPersistenceMapper;


    @Test
    void getById_whenNotFound_then_empty() {

        // Given
        when(productRepository.findById(any()))
                .thenReturn(Optional.empty());

        // When
        final var product = productPersistenceAdapter.getById(UUID.randomUUID());

        // Then
        assertThat(product).isNotNull();
        assertThat(product).isEmpty();

        verify(productRepository).findById(any());
        verify(productPersistenceMapper, never()).toDomain(any());
    }

    @Test
    void getById_when_nominal() {
        final var productEntity = ProductSampleTest.entity();
        final var productDomain = ProductSampleTest.domain();

        // Given
        when(productRepository.findById(productEntity.getId()))
                .thenReturn(Optional.of(productEntity));
        when(productPersistenceMapper.toDomain(productEntity))
                .thenReturn(productDomain);

        // When
        final var productReturned = productPersistenceAdapter.getById(productEntity.getId());

        // Then
        assertThat(productReturned).isNotNull();
        assertThat(productReturned).isPresent();
        assertThat(productReturned).contains(productDomain);

        verify(productRepository).findById(productEntity.getId());
        verify(productPersistenceMapper).toDomain(productEntity);
    }

    @Test
    void save_when_nominal() {
        final var productDomain = ProductSampleTest.domain()
                .toBuilder()
                .id(null)
                .build();
        final var productEntity = ProductSampleTest.entity();
        productEntity.setId(null);

        // Given
        when(productPersistenceMapper.toEntity(productDomain))
                .thenReturn(productEntity);
        when(productPersistenceMapper.toDomain(productEntity))
                .thenReturn(productDomain);
        when(productRepository.save(productEntity))
                .thenReturn(productEntity);

        // When
        final var productSaved = productPersistenceAdapter.save(productDomain);

        // Then
        assertThat(productSaved).isNotNull();
        assertThat(productSaved).isEqualTo(productDomain);

        verify(productPersistenceMapper).toEntity(productDomain);
        verify(productPersistenceMapper).toDomain(productEntity);
        verify(productRepository).save(productEntity);
    }

}