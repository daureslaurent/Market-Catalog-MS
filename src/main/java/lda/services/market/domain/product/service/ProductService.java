package lda.services.market.domain.product.service;

import lda.services.market.domain.product.exception.ProductNotFoundException;
import lda.services.market.domain.product.model.Product;
import lda.services.market.domain.product.port.ProductInput;
import lda.services.market.domain.product.port.ProductReadOutput;
import lda.services.market.domain.product.port.ProductWriteOutput;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@AllArgsConstructor
public class ProductService implements ProductInput {

    private final ProductReadOutput productReadOutput;
    private final ProductWriteOutput productWriteOutput;

    @Override
    public Product retrieveById(UUID id) {
        return productReadOutput.getById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public Page<Product> retrieveByPage(Pageable page) {
        return productReadOutput.getByPage(page);
    }

    @Override
    public Product addProduct(Product product) {
        return productWriteOutput.save(product);
    }

    @Override
    public Product updateQuantity(UUID id, int quantity) {
        final var product = productWriteOutput.getById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        final var updatedProduct = product.
                changeQuantity(quantity);

        return productWriteOutput.save(updatedProduct);
    }
}
