package lda.services.market.application.api.rest.product.mapper;

import lda.services.market.application.api.rest.product.model.ProductCreateRequest;
import lda.services.market.application.api.rest.product.model.ProductDetalResponse;
import lda.services.market.application.api.rest.product.model.ProductResponse;
import lda.services.market.domain.product.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductRestMapper {
    ProductResponse toProductResponse(final Product product);
    ProductDetalResponse toProductDetailResponse(final Product product);

    Product toProduct(final ProductCreateRequest productCreateRequest);

}
