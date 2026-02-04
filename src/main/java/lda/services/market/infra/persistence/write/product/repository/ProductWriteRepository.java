package lda.services.market.infra.persistence.write.product.repository;

import lda.services.market.infra.persistence.write.product.entity.ProductWriteEntity;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;


@NullMarked
@Repository
@Transactional(transactionManager = "writeTransactionManager")
public interface ProductWriteRepository extends CrudRepository<ProductWriteEntity, UUID> {

//    @EntityGraph(attributePaths = "tags")
    Optional<ProductWriteEntity> findById(final UUID id);
}
