package lda.services.market.infra.persistence.product.mapper;

import lda.services.market.domain.product.model.Product;
import lda.services.market.infra.persistence.product.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductPersistenceMapper {
    Product toDomain(final ProductEntity productEntity);
    ProductEntity toEntity(final Product product);
}
