package lda.services.market.infra.persistence.projection;

import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record ProductChangeQuantityEvent(
        UUID idProduct,
        Integer changeValue,
        boolean isIncrease
) {
}
