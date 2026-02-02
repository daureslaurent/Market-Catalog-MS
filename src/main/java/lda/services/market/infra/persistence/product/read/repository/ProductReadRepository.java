package lda.services.market.infra.persistence.product.read.repository;

import lda.services.market.infra.persistence.product.read.entity.ProductReadEntity;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@NullMarked
@Repository
public interface ProductReadRepository extends
        PagingAndSortingRepository<ProductReadEntity, UUID>,
        CrudRepository<ProductReadEntity, UUID> {
    @EntityGraph(attributePaths = "tags")
    Page<ProductReadEntity> findAll(final Pageable pageable);

    @EntityGraph(attributePaths = "tags")
    Optional<ProductReadEntity> findById(final UUID id);
}
