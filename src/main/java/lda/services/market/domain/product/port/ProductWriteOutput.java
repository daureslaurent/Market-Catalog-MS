package lda.services.market.domain.product.port;

import lda.services.market.domain.product.model.Product;

import java.util.Optional;
import java.util.UUID;

public interface ProductWriteOutput {
    Optional<Product> getById(final UUID id);
    Product save(final Product product);
    Product create(final Product product);
}
