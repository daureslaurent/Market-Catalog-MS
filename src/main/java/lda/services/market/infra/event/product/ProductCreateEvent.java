package lda.services.market.infra.event.product;

import lda.services.market.domain.product.model.Product;
import lombok.Builder;

@Builder(toBuilder = true)
public record ProductCreateEvent (
        Product product
) {
}
