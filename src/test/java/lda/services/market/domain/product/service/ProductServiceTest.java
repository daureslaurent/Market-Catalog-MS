package lda.services.market.domain.product.service;

import lda.services.market.domain.product.ProductSampleTest;
import lda.services.market.domain.product.exception.ProductNotFoundException;
import lda.services.market.domain.product.exception.ProductQuantityTooSmallException;
import lda.services.market.domain.product.port.ProductReadOutput;
import lda.services.market.domain.product.port.ProductWriteOutput;
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
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductReadOutput productReadOutput;

    @Mock
    private ProductWriteOutput productWriteOutput;

    @Test
    void should_retrieve_product() {
        final var product = ProductSampleTest.domain();

        // Given
        when(productReadOutput.getById(product.id()))
                .thenReturn(Optional.of(product));

        // When
        final var productTest = productService.retrieveById(product.id());

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
        final var throwed = assertThrows(ProductNotFoundException.class, () -> productService.retrieveById(fakeId));

        // Then
        assertThat(throwed).isNotNull();
        assertThat(throwed).isInstanceOf(ProductNotFoundException.class);

        verify(productReadOutput).getById(fakeId);
    }

    @Test
    void should_add_product_when_ok() {
        final var product = ProductSampleTest.domain()
                .toBuilder()
                .id(null)
                .build();
        final var productReturned = ProductSampleTest.domain();

        // Given
        when(productWriteOutput.save(product))
                .thenReturn(productReturned);

        // When
        final var productAdded = productService.addProduct(product);

        // Then
        assertThat(productAdded).isNotNull();
        assertThat(productAdded).isEqualTo(productReturned);
        assertThat(productAdded.id()).isNotNull();

        verify(productWriteOutput).save(product);
    }

    @Test
    void should_updateQuantity_when_ok() {
        final var product = ProductSampleTest.domain();
        final var quantity = 200;
        final var productWanted = product.toBuilder()
                .quantity(quantity)
                .build();

        // Given
        when(productWriteOutput.getById(product.id()))
                .thenReturn(Optional.of(product));
        when(productWriteOutput.save(productWanted))
                .thenReturn(productWanted);

        // When
        final var productTest = productService.updateQuantity(product.id(), quantity);

        // Then
        assertThat(productTest).isNotNull();
        assertThat(productTest).isEqualTo(productWanted);

        verify(productReadOutput, never()).getById(product.id());

        verify(productWriteOutput).getById(product.id());
        verify(productWriteOutput).save(productWanted);

    }

    @Test
    void updateQuantity_should_throw_when_tooSmall() {
        final var product = ProductSampleTest.domain();
        final var productId = product.id();
        final var quantity = -5;

        // Given
        when(productWriteOutput.getById(product.id()))
                .thenReturn(Optional.of(product));

        // When
        final var throwed = assertThrows(ProductQuantityTooSmallException.class, () ->
                productService.updateQuantity(productId, quantity));

        // Then
        assertThat(throwed).isNotNull();
        assertThat(throwed).isInstanceOf(ProductQuantityTooSmallException.class);

        verify(productReadOutput, never()).getById(product.id());

        verify(productWriteOutput).getById(product.id());
        verify(productWriteOutput, never()).save(any());
    }









}