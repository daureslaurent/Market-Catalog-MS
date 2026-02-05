package lda.services.market.infra.persistence.write.product.outbox;

import com.lda.streambox.entity.StreamBoxBaseStatusEnum;
import com.lda.streambox.model.StreamBoxEvent;
import com.lda.streambox.port.StreamBoxInput;
import lda.services.market.domain.product.model.Product;
import lda.services.market.infra.persistence.projection.ProductChangeQuantityEvent;
import lda.services.market.infra.persistence.write.product.outbox.entity.ProductOutboxEventEntity;
import lda.services.market.infra.persistence.write.product.outbox.mapper.ProductOutboxMapper;
import lda.services.market.infra.persistence.projection.ProductCreateEvent;
import lda.services.market.infra.persistence.write.product.outbox.repository.ProductOutboxRepository;
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
        this.addToBox(mapper.toEntity(outboxEvent));
    }

    public void addEvent(ProductChangeQuantityEvent event) {
        final var streamEvent = StreamBoxEvent.builder()
                .type(ProductChangeQuantityEvent.class.getSimpleName())
                .payload(event)
                .build();
        this.addToBox(mapper.toEntity(streamEvent));
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
    public void addToBox(ProductOutboxEventEntity productOutboxEventEntity) {
        productOutboxRepository.save(productOutboxEventEntity);
    }

}
