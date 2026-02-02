package lda.services.market.domain.product.port;

import lda.services.market.domain.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductInput {
    Product retrieveById(final UUID id);
    Page<Product> retrieveByPage(final Pageable page);
    Product addProduct(final Product product);
    Product updateQuantity(final UUID id, final int quantity);
}
