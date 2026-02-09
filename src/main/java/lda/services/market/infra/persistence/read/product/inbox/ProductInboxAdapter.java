package lda.services.market.infra.persistence.read.product.inbox;

import com.lda.streambox.InboxAdapter;
import com.lda.streambox.json.JsonConverter;
import com.lda.streambox.model.StreamBoxEvent;
import com.lda.streambox.repository.StreamBoxRepository;
import lda.services.market.infra.persistence.projection.product.ProductChangeQuantityEvent;
import lda.services.market.infra.persistence.projection.product.ProductCreateEvent;
import lda.services.market.infra.persistence.read.product.ProductProjectionAdapter;
import lda.services.market.infra.persistence.read.product.inbox.entity.ProductInboxEventEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public class ProductInboxAdapter extends InboxAdapter<ProductInboxEventEntity, StreamBoxEvent> {

    private final ProductProjectionAdapter projectionAdapter;

    public ProductInboxAdapter(
            JsonConverter jsonConverter,
            StreamBoxRepository<ProductInboxEventEntity> streamBoxRepository,
            InboxFactory streamBoxFactory,
            ProductProjectionAdapter projectionAdapter) {
        super(jsonConverter, streamBoxRepository, streamBoxFactory);
        this.projectionAdapter = projectionAdapter;
    }

    @Transactional(transactionManager = "readTransactionManager")
    public void doHandle(ProductInboxEventEntity entity) {
        this.handleEvent(entity);
    }

    @Override
    protected void handleProjection(StreamBoxEvent streamBoxEvent) {
        log.info("Consuming event {}", streamBoxEvent);
        switch (streamBoxEvent) {
            case ProductCreateEvent e -> projectionAdapter.createProductProjection(e);
            case ProductChangeQuantityEvent e -> projectionAdapter.changeQuantityProjection(e);
            default -> log.error("Event type not handled: {}", streamBoxEvent);
        }
    }

}
