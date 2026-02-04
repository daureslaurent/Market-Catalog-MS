package lda.services.market.domain.product.port;

import lda.services.market.domain.product.model.Product;

import java.util.Optional;
import java.util.UUID;

public interface ProductWriteOutput {
    Optional<Product> getById(final UUID id);

    // Commands
    Product create(final Product product);
    Product changeQuantity(final UUID idProduct, final Integer value);
}
