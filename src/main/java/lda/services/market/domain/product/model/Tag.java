package lda.services.market.domain.product.model;

import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record Tag(
        UUID id,
        String name
) {
}
