package lda.services.market.domain.product.service;

import lda.services.market.domain.product.ProductSampleTest;
import lda.services.market.domain.product.exception.ProductNotFoundException;
import lda.services.market.domain.product.port.ProductReadOutput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProductQueryServiceTest {

    @InjectMocks
    private ProductQueryService productQueryService;

    @Mock
    private ProductReadOutput productReadOutput;

    @Test
    void should_retrieve_product() {
        final var product = ProductSampleTest.domain();

        // Given
        when(productReadOutput.getById(product.id()))
                .thenReturn(Optional.of(product));

        // When
        final var productTest = productQueryService.retrieveById(product.id());

        // Then
        assertThat(productTest).isNotNull();
        assertThat(productTest).isEqualTo(product);

        verify(productReadOutput).getById(product.id());
    }

    @Test
    void should_throw_notFoundException_when_empty() {

        final var fakeId = UUID.randomUUID();

        // Given
        when(productReadOutput.getById(any()))
                .thenReturn(Optional.empty());

        // When
        final var thrown = assertThrows(ProductNotFoundException.class, () -> productQueryService.retrieveById(fakeId));

        // Then
        assertThat(thrown).isNotNull();
        assertThat(thrown).isInstanceOf(ProductNotFoundException.class);

        verify(productReadOutput).getById(fakeId);
    }










}