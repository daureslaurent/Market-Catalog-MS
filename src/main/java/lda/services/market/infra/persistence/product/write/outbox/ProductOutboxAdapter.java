package lda.services.market.infra.persistence.product.write.outbox;

import lda.services.market.domain.product.model.Product;
import lda.services.market.infra.persistence.streambox.entity.StreamBoxBaseStatusEnum;
import lda.services.market.infra.persistence.streambox.StreamBoxEvent;
import lda.services.market.infra.persistence.streambox.StreamBoxInput;
import lda.services.market.infra.persistence.product.write.outbox.entity.ProductOutboxEventEntity;
import lda.services.market.infra.persistence.product.write.outbox.mapper.ProductOutboxMapper;
import lda.services.market.infra.persistence.product.event.ProductCreateEvent;
import lda.services.market.infra.persistence.product.write.outbox.repository.ProductOutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ProductOutboxAdapter implements StreamBoxInput<ProductOutboxEventEntity> {

    private final ProductOutboxRepository productOutboxRepository;
    private final ProductOutboxMapper mapper;

    public void createProductEvent(final Product product) {
        final var event = ProductCreateEvent.builder()
                .product(product)
                .build();
        final var outboxEvent = StreamBoxEvent.builder()
                .type(event.getClass().getSimpleName())
                .payload(event)
                .build();
        final var outboxEntity = mapper.toEntity(outboxEvent);
        productOutboxRepository.save(outboxEntity);
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

}
