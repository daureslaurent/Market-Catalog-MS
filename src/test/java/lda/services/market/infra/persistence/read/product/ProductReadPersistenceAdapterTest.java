package lda.services.market.infra.persistence.read.product;

import lda.services.market.domain.product.ProductSampleTest;
import lda.services.market.infra.persistence.read.product.mapper.ProductReadPersistenceMapper;
import lda.services.market.infra.persistence.read.product.repository.ProductReadRepository;
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
class ProductReadPersistenceAdapterTest {

    @InjectMocks
    private ProductReadPersistenceAdapter productPersistenceAdapter;

    @Mock
    private ProductReadRepository productReadRepository;

    @Mock
    private ProductReadPersistenceMapper productReadPersistenceMapper;


    @Test
    void getById_whenNotFound_then_empty() {

        // Given
        when(productReadRepository.findById(any()))
                .thenReturn(Optional.empty());

        // When
        final var product = productPersistenceAdapter.getById(UUID.randomUUID());

        // Then
        assertThat(product).isNotNull();
        assertThat(product).isEmpty();

        verify(productReadRepository).findById(any());
        verify(productReadPersistenceMapper, never()).toDomain(any());
    }

    @Test
    void getById_when_nominal() {
        final var productEntity = ProductSampleTest.entityRead();
        final var productDomain = ProductSampleTest.domain();

        // Given
        when(productReadRepository.findById(productEntity.getId()))
                .thenReturn(Optional.of(productEntity));
        when(productReadPersistenceMapper.toDomain(productEntity))
                .thenReturn(productDomain);

        // When
        final var productReturned = productPersistenceAdapter.getById(productEntity.getId());

        // Then
        assertThat(productReturned).isNotNull();
        assertThat(productReturned).isPresent();
        assertThat(productReturned).contains(productDomain);

        verify(productReadRepository).findById(productEntity.getId());
        verify(productReadPersistenceMapper).toDomain(productEntity);
    }

}