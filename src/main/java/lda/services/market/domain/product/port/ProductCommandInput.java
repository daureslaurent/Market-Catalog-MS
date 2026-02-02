package lda.services.market.domain.product.port;

import lda.services.market.domain.product.model.Product;

import java.util.UUID;

public interface ProductCommandInput {
    Product addProduct(final Product product);
    Product updateQuantity(final UUID id, final int quantity);
}
