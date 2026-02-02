package lda.services.market.domain.product.port;

import lda.services.market.domain.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductQueryInput {
    Product retrieveById(final UUID id);
    Page<Product> retrieveByPage(final Pageable page);
}
