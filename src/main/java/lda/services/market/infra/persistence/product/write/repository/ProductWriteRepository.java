package lda.services.market.infra.persistence.product.write.repository;

import lda.services.market.infra.persistence.product.write.entity.ProductWriteEntity;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@NullMarked
@Repository
public interface ProductWriteRepository extends CrudRepository<ProductWriteEntity, UUID> {

    @EntityGraph(attributePaths = "tags")
    Optional<ProductWriteEntity> findById(final UUID id);
}
