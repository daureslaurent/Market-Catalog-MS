package lda.services.market.infra.persistence.projection.product;

import com.lda.streambox.anotation.StreamBoxEventType;
import com.lda.streambox.model.StreamBoxEvent;
import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
@StreamBoxEventType("ProductChangeQuantityEvent")
public record ProductChangeQuantityEvent(
        UUID idProduct,
        Integer changeValue,
        boolean isIncrease
) implements StreamBoxEvent {
}
