package lda.services.market.domain.product.port;

import lda.services.market.domain.product.model.Product;

import java.util.Optional;
import java.util.UUID;

public interface ProductWriteOutput {
    Optional<Product> getById(final UUID id);
    /**
     * @deprecated (do not implement outbox+CQRS pattern -> change save() to a command)
     */
    @Deprecated(forRemoval = true)
    Product save(final Product product);
    Product create(final Product product);
}
