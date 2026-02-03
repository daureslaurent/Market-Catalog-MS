package lda.services.market.infra.persistence.product.write.outbox.repository;

import lda.services.market.infra.persistence.product.write.outbox.entity.ProductOutboxEventEntity;
import lda.services.market.infra.persistence.streambox.repository.StreamBoxRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOutboxRepository extends StreamBoxRepository<ProductOutboxEventEntity> {
}
