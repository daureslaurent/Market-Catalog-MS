package lda.services.market.infra.persistence.write.product.outbox;

import com.lda.streambox.factory.OutboxFactoryInterface;
import com.lda.streambox.json.JsonConverter;
import com.lda.streambox.model.StreamBoxEvent;
import com.lda.streambox.model.StreamBoxWrapper;
import lda.services.market.infra.persistence.write.product.outbox.entity.ProductOutboxEventEntity;
import org.springframework.stereotype.Component;

@Component
public class OutboxFactory implements OutboxFactoryInterface<ProductOutboxEventEntity, StreamBoxWrapper<StreamBoxEvent>> {

    @Override
    public ProductOutboxEventEntity createEntity(StreamBoxWrapper<StreamBoxEvent> wrappedEvent, JsonConverter jsonConverter) {
        return ProductOutboxEventEntity.builder()
                .id(wrappedEvent.id())
                .type(wrappedEvent.type())
                .payload(jsonConverter.toJson(wrappedEvent.payload()))
                .build();
    }

}
