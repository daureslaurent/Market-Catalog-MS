package lda.services.market.infra.persistence.product.mapper;

import lda.services.market.domain.product.model.Tag;
import lda.services.market.infra.persistence.product.entity.TagEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagPersistenceMapper {
    Tag toDomain(final TagEntity tagEntity);
    TagEntity toEntity(final Tag tag);
}
