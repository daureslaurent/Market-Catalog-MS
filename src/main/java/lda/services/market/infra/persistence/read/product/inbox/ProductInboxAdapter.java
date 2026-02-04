package lda.services.market.infra.persistence.read.product.inbox;

import com.lda.streambox.entity.StreamBoxBaseStatusEnum;
import com.lda.streambox.model.StreamBoxEvent;
import com.lda.streambox.port.StreamBoxInput;
import lda.services.market.infra.event.product.ProductCreateEvent;
import lda.services.market.infra.persistence.read.product.ProductReadPersistenceAdapter;
import lda.services.market.infra.persistence.read.product.inbox.entity.ProductInboxEventEntity;
import lda.services.market.infra.persistence.read.product.inbox.mapper.ProductInboxMapper;
import lda.services.market.infra.persistence.read.product.inbox.repository.ProductInboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class ProductInboxAdapter implements StreamBoxInput<ProductInboxEventEntity> {

    private final ProductInboxRepository productInboxRepository;
    private final ProductInboxMapper mapper;

    private final ProductReadPersistenceAdapter productReadPersistenceAdapter;

    public void createProductEvent(final ProductInboxEventEntity event) {
        final StreamBoxEvent<ProductCreateEvent> e = mapper.toCreateEvent(event);
        productReadPersistenceAdapter.createProduct(e.payload().product());
    }

    @Override
    public List<ProductInboxEventEntity> lockNextBatch(int limit) {
        return productInboxRepository.lockNextBatch(limit);
    }

    @Override
    public void finish(ProductInboxEventEntity outboxEvent) {
        outboxEvent.setStatus(StreamBoxBaseStatusEnum.FINISHED);
        productInboxRepository.save(outboxEvent);
    }

    @Override
    public void addToBox(ProductInboxEventEntity event) {
        productInboxRepository.save(event);
    }

}
