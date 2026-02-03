package lda.services.market.infra.persistence.product.write.outbox.repository;

import com.lda.streambox.repository.StreamBoxRepository;
import lda.services.market.infra.persistence.product.write.outbox.entity.ProductOutboxEventEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOutboxRepository extends StreamBoxRepository<ProductOutboxEventEntity> {
}
