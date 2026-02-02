package lda.services.market.application.api.rest.product.model;

import lda.services.market.domain.product.model.Tag;
import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder(toBuilder = true)
public record ProductDetalResponse(
        UUID id,
        String name,
        String detail,
        Integer quantity,
        String pictureId,
        Set<Tag> tags
) {
}
