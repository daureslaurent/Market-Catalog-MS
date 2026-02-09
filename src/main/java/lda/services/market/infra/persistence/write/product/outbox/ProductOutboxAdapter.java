package lda.services.market.infra.persistence.write.product.outbox;

import com.lda.streambox.OutboxAdapter;
import com.lda.streambox.factory.OutboxFactoryInterface;
import com.lda.streambox.json.JsonConverter;
import com.lda.streambox.model.StreamBoxEvent;
import com.lda.streambox.model.StreamBoxWrapper;
import com.lda.streambox.repository.StreamBoxRepository;
import lda.services.market.infra.persistence.write.product.outbox.entity.ProductOutboxEventEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class ProductOutboxAdapter extends OutboxAdapter<ProductOutboxEventEntity, StreamBoxEvent> {

    private final FakeKafkaContainer fakeKafkaContainer;

    protected ProductOutboxAdapter(
            JsonConverter jsonConverter,
            StreamBoxRepository<ProductOutboxEventEntity> streamBoxRepository,
            OutboxFactoryInterface<ProductOutboxEventEntity, StreamBoxWrapper<StreamBoxEvent>> factoryInterface,
            FakeKafkaContainer fakeKafkaContainer) {
        super(jsonConverter, streamBoxRepository, factoryInterface);
        this.fakeKafkaContainer = fakeKafkaContainer;
    }

    @Override
    protected void sendToMessaging(String json) {
        log.info("Produce event (sendToMessaging)");
        // Send event to kafka

        // Test Impl
        log.info("Faking kafka ... {}", json);
        fakeKafkaContainer.addJson(json);
    }

    @Transactional(transactionManager = "writeTransactionManager")
    public void doHandle(ProductOutboxEventEntity entity) {
        this.handleEvent(entity);
    }
}
