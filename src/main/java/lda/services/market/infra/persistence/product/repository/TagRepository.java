package lda.services.market.infra.persistence.product.repository;

import lda.services.market.infra.persistence.product.entity.TagEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TagRepository extends
        PagingAndSortingRepository<TagEntity, UUID>,
        CrudRepository<TagEntity, UUID> {
}
