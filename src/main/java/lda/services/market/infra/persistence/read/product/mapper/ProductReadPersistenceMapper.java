package lda.services.market.infra.persistence.read.product.mapper;

import lda.services.market.domain.product.model.Product;
import lda.services.market.infra.persistence.read.product.entity.ProductReadEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductReadPersistenceMapper {
    Product toDomain(final ProductReadEntity productReadEntity);
    ProductReadEntity toEntity(final Product product);
}
