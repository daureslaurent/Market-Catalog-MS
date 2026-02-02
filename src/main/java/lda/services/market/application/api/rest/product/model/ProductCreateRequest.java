package lda.services.market.application.api.rest.product.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record ProductCreateRequest(
        String name,
        String detail,
        Integer quantity
) {
}
