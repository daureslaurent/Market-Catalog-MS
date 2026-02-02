package lda.services.market.infra.persistence.product.read.mapper;

import lda.services.market.domain.product.model.Tag;
import lda.services.market.infra.persistence.product.read.entity.TagReadEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagReadPersistenceMapper {
    Tag toDomain(final TagReadEntity tagReadEntity);
    TagReadEntity toEntity(final Tag tag);
}
