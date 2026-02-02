package lda.services.market.application.api.rest.product.model;

import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record ProductResponse(
        UUID id,
        String name,
        String detail,
        String pictureId
) {
}
