package lda.services.market.domain.product.service;

import lda.services.market.domain.product.exception.ProductNotFoundException;
import lda.services.market.domain.product.model.Product;
import lda.services.market.domain.product.port.ProductQueryInput;
import lda.services.market.domain.product.port.ProductReadOutput;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@AllArgsConstructor
public class ProductQueryService implements ProductQueryInput {

    private final ProductReadOutput productReadOutput;

    @Override
    public Product retrieveById(UUID id) {
        return productReadOutput.getById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public Page<Product> retrieveByPage(Pageable page) {
        return productReadOutput.getByPage(page);
    }

}
