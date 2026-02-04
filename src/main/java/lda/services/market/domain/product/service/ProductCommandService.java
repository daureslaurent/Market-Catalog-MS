package lda.services.market.domain.product.service;

import lda.services.market.domain.product.exception.ProductNotFoundException;
import lda.services.market.domain.product.model.Product;
import lda.services.market.domain.product.port.ProductCommandInput;
import lda.services.market.domain.product.port.ProductWriteOutput;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class ProductCommandService implements ProductCommandInput {

    private final ProductWriteOutput productWriteOutput;

    @Override
    public Product addProduct(Product product) {
        return productWriteOutput.create(product);
    }

    @Override
    public Product updateQuantity(UUID id, int quantity) {
        final var product = productWriteOutput.getById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        final var updatedProduct = product.
                changeQuantity(quantity);
        return productWriteOutput.changeQuantity(id, updatedProduct.quantity());
    }
}
