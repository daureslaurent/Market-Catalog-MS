package lda.services.market.domain.product.service;

import lda.services.market.domain.product.exception.ProductNotFoundException;
import lda.services.market.domain.product.model.Product;
import lda.services.market.domain.product.port.ProductInput;
import lda.services.market.domain.product.port.ProductOutput;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@AllArgsConstructor
public class ProductService implements ProductInput {

    private final ProductOutput productOutput;

    @Override
    public Product retrieveById(UUID id) {
        return productOutput.getById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public Page<Product> retrieveByPage(Pageable page) {
        return productOutput.getByPage(page);
    }

    @Override
    public Product addProduct(Product product) {
        return productOutput.save(product);
    }

    @Override
    public Product updateQuantity(UUID id, int quantity) {
        final var product = productOutput.getById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        final var updatedProduct = product.
                changeQuantity(quantity);

        return productOutput.save(updatedProduct);
    }
}
