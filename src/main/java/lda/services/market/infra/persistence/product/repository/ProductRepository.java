package lda.services.market.infra.persistence.product.repository;

import lda.services.market.infra.persistence.product.entity.ProductEntity;
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
public interface ProductRepository extends
        PagingAndSortingRepository<ProductEntity, UUID>,
        CrudRepository<ProductEntity, UUID> {
    @EntityGraph(attributePaths = "tags")
    Page<ProductEntity> findAll(final Pageable pageable);

    @EntityGraph(attributePaths = "tags")
    Optional<ProductEntity> findById(final UUID id);
}
