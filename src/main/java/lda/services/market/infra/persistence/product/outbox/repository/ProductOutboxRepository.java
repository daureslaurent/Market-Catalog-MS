package lda.services.market.infra.persistence.product.outbox.repository;

import lda.services.market.infra.persistence.product.outbox.entity.ProductOutboxEventEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductOutboxRepository extends CrudRepository<ProductOutboxEventEntity, UUID> {
}
