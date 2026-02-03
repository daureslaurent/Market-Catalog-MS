package lda.services.market.infra.persistence.product.read.inbox.repository;

import com.lda.streambox.repository.StreamBoxRepository;
import lda.services.market.infra.persistence.product.read.inbox.entity.ProductInboxEventEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInboxRepository extends StreamBoxRepository<ProductInboxEventEntity> {
}
