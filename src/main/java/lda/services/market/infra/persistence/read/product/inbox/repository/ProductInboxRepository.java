package lda.services.market.infra.persistence.read.product.inbox.repository;

import com.lda.streambox.repository.StreamBoxRepository;
import lda.services.market.infra.persistence.read.product.inbox.entity.ProductInboxEventEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(transactionManager = "readTransactionManager")
public interface ProductInboxRepository extends StreamBoxRepository<ProductInboxEventEntity> {
}
