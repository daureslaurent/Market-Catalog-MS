package lda.services.market.infra.persistence.product.outbox;

import lda.services.market.domain.product.model.Product;
import lda.services.market.infra.persistence.product.outbox.mapper.ProductOutboxMapper;
import lda.services.market.infra.persistence.product.outbox.event.ProductCreateEvent;
import lda.services.market.infra.persistence.product.outbox.model.ProductOutboxEvent;
import lda.services.market.infra.persistence.product.outbox.repository.ProductOutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductOutboxAdapter {

    private final ProductOutboxRepository productOutboxRepository;
    private final ProductOutboxMapper mapper;

    public void createProductEvent(final Product product) {
        final var event = ProductCreateEvent.builder()
                .product(product)
                .build();
        final var outboxEvent = ProductOutboxEvent.builder()
                .type(event.getClass().getSimpleName())
                .payload(event)
                .build();
        final var outboxEntity = mapper.toEntity(outboxEvent);
        productOutboxRepository.save(outboxEntity);
    }
}
