package lda.services.market.infra.persistence.write.product.outbox.repository;

import com.lda.streambox.repository.StreamBoxRepository;
import lda.services.market.infra.persistence.write.product.outbox.entity.ProductOutboxEventEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(transactionManager = "writeTransactionManager")
public interface ProductOutboxRepository extends StreamBoxRepository<ProductOutboxEventEntity> {
}
