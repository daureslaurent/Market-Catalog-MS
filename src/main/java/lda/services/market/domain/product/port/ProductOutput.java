package lda.services.market.domain.product.port;

import lda.services.market.domain.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ProductOutput {

    Page<Product> getByPage(final Pageable page);
    Optional<Product> getById(final UUID id);
    Product save(final Product product);

}
