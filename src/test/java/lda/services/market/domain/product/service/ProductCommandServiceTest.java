package lda.services.market.domain.product.service;

import lda.services.market.domain.product.ProductSampleTest;
import lda.services.market.domain.product.exception.ProductQuantityTooSmallException;
import lda.services.market.domain.product.port.ProductWriteOutput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProductCommandServiceTest {

    @InjectMocks
    private ProductCommandService productCommandService;

    @Mock
    private ProductWriteOutput productWriteOutput;

    @Test
    void should_add_product_when_ok() {
        final var product = ProductSampleTest.domain()
                .toBuilder()
                .id(null)
                .build();
        final var productReturned = ProductSampleTest.domain();

        // Given
        when(productWriteOutput.create(product))
                .thenReturn(productReturned);

        // When
        final var productAdded = productCommandService.addProduct(product);

        // Then
        assertThat(productAdded).isNotNull();
        assertThat(productAdded).isEqualTo(productReturned);
        assertThat(productAdded.id()).isNotNull();

        verify(productWriteOutput).create(product);
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
        when(productWriteOutput.changeQuantity(productWanted.id(), productWanted.quantity()))
                .thenReturn(productWanted);

        // When
        final var productTest = productCommandService.updateQuantity(product.id(), quantity);

        // Then
        assertThat(productTest).isNotNull();
        assertThat(productTest).isEqualTo(productWanted);

        verify(productWriteOutput).getById(product.id());
        verify(productWriteOutput).changeQuantity(productWanted.id(), productWanted.quantity());

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
        final var thrown = assertThrows(ProductQuantityTooSmallException.class, () ->
                productCommandService.updateQuantity(productId, quantity));

        // Then
        assertThat(thrown).isNotNull();
        assertThat(thrown).isInstanceOf(ProductQuantityTooSmallException.class);

        verify(productWriteOutput).getById(product.id());
        verify(productWriteOutput, never()).changeQuantity(any(), any());
    }

}