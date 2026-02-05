package lda.services.market.infra.persistence.write.product.outbox;

import com.lda.streambox.entity.StreamBoxBaseStatusEnum;
import com.lda.streambox.json.JsonConverter;
import com.lda.streambox.model.StreamBoxEvent;
import com.lda.streambox.port.StreamBoxInput;
import lda.services.market.domain.product.model.Product;
import lda.services.market.infra.persistence.projection.product.ProductChangeQuantityEvent;
import lda.services.market.infra.persistence.write.product.outbox.entity.ProductOutboxEventEntity;
import lda.services.market.infra.persistence.write.product.outbox.mapper.ProductOutboxMapper;
import lda.services.market.infra.persistence.projection.product.ProductCreateEvent;
import lda.services.market.infra.persistence.write.product.outbox.repository.ProductOutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class ProductOutboxAdapter implements StreamBoxInput<ProductOutboxEventEntity> {

    private final ProductOutboxRepository productOutboxRepository;
    private final ProductOutboxMapper mapper;

    private final JsonConverter jsonConverter;
    private final FakeKafkaContainer fakeKafkaContainer;

    public void createProductEvent(final Product product) {
        final var event = ProductCreateEvent.builder()
                .product(product)
                .build();
        final var outboxEvent = StreamBoxEvent.builder()
                .type(event.getClass().getSimpleName())
                .payload(event)
                .build();
        this.addEvent(mapper.toEntity(outboxEvent));
    }

    public void addEvent(ProductChangeQuantityEvent event) {
        final var streamEvent = StreamBoxEvent.builder()
                .type(ProductChangeQuantityEvent.class.getSimpleName())
                .payload(event)
                .build();
        this.addEvent(mapper.toEntity(streamEvent));
    }

    @Override
    public List<ProductOutboxEventEntity> lockNextBatch(int limit) {
        return productOutboxRepository.lockNextBatch(limit);
    }

    @Override
    public void finish(ProductOutboxEventEntity outboxEvent) {
        outboxEvent.setStatus(StreamBoxBaseStatusEnum.FINISHED);
        productOutboxRepository.save(outboxEvent);
    }

    @Override
    public void addEvent(ProductOutboxEventEntity productOutboxEventEntity) {
        productOutboxRepository.save(productOutboxEventEntity);
    }

    @Override
    @Transactional(transactionManager = "writeTransactionManager")
    public void handleEvent(ProductOutboxEventEntity productOutboxEventEntity) {
        log.info("Produce event {}", productOutboxEventEntity.getId());
        // Send event to kafka

        // Test Impl
        final var jsonKafka = jsonConverter.toJson(productOutboxEventEntity);
        log.info("Faking kafka ... {}", jsonKafka);
        fakeKafkaContainer.addJson(jsonKafka);

        // Delete event from outbox
        this.finish(productOutboxEventEntity);
        log.info("Finishing outbox event {}", productOutboxEventEntity.getId());
    }

}
