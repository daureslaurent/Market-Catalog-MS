package lda.services.market.infra.persistence.projection.product;

import com.lda.streambox.anotation.StreamBoxEventType;
import com.lda.streambox.model.StreamBoxEvent;
import lda.services.market.domain.product.model.Product;
import lombok.Builder;

@Builder(toBuilder = true)
@StreamBoxEventType("ProductCreateEvent")
public record ProductCreateEvent (
        Product product
) implements StreamBoxEvent {
}
