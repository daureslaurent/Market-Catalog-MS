package lda.services.market.infra.persistence.product.outbox.model;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ProductOutboxEvent<T>(
        UUID id,
        String type,
        T payload
) {
}
