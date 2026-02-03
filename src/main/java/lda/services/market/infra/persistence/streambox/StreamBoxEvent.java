package lda.services.market.infra.persistence.streambox;

import lombok.Builder;

import java.util.UUID;

@Builder
public record StreamBoxEvent<T>(
        UUID id,
        String type,
        T payload
) {
}
