package lda.services.market.infra.persistence.write.product.mapper;

import lda.services.market.domain.product.model.Product;
import lda.services.market.infra.persistence.write.product.entity.ProductWriteEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductWritePersistenceMapper {
    Product toDomain(final ProductWriteEntity productReadEntity);
    ProductWriteEntity toEntity(final Product product);
}
