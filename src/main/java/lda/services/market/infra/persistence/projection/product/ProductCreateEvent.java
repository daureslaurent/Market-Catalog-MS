package lda.services.market.infra.persistence.projection.product;

import lda.services.market.domain.product.model.Product;
import lombok.Builder;

@Builder(toBuilder = true)
public record ProductCreateEvent (
        Product product
) {
}
