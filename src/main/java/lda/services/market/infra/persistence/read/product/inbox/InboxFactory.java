package lda.services.market.infra.persistence.read.product.inbox;

import com.lda.streambox.factory.InboxFactoryAbstract;
import com.lda.streambox.json.JsonConverter;
import com.lda.streambox.model.StreamBoxEvent;
import com.lda.streambox.register.StreamBoxEventRegistry;
import lda.services.market.infra.persistence.read.product.inbox.entity.ProductInboxEventEntity;
import org.springframework.stereotype.Component;

@Component
public class InboxFactory extends InboxFactoryAbstract<ProductInboxEventEntity, StreamBoxEvent> {

    public InboxFactory(StreamBoxEventRegistry registry) {
        super(registry, ProductInboxEventEntity.class);
    }

    @Override
    public ProductInboxEventEntity createEntity(String s, JsonConverter jsonConverter) {
        final var entity = jsonConverter.fromJson(s, ProductInboxEventEntity.class);
        entity.setRefOutbox(entity.getId());
        entity.setId(null);
        return entity;
    }
}
