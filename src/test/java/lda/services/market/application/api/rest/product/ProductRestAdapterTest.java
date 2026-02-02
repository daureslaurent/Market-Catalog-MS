package lda.services.market.application.api.rest.product;

import lda.services.market.application.api.rest.product.mapper.ProductRestMapper;
import lda.services.market.application.api.rest.product.model.ProductCreateRequest;
import lda.services.market.application.api.rest.product.model.ProductDetalResponse;
import lda.services.market.application.api.rest.product.model.ProductResponse;
import lda.services.market.domain.product.ProductSampleTest;
import lda.services.market.domain.product.model.Product;
import lda.services.market.domain.product.port.ProductInput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ProductRestAdapterTest {

    @InjectMocks
    private ProductRestAdapter productRestAdapter;

    @Mock
    private ProductInput productInput;

    @Mock
    private ProductRestMapper productRestMapper;

    @Test
    void getProductByPage_when_nominal() {
        final var productDomain0 = ProductSampleTest.domain();
        final var productRes0 = ProductDetalResponse.builder()
                .id(productDomain0.id())
                .name(productDomain0.name())
                .detail(productDomain0.detail())
                .quantity(productDomain0.quantity())
                .pictureId(productDomain0.pictureId())
                .tags(productDomain0.tags())
                .build();
        final var productDomain1 = ProductSampleTest.domain();
        final var productRes1 = ProductDetalResponse.builder()
                .id(productDomain1.id())
                .name(productDomain1.name())
                .detail(productDomain1.detail())
                .quantity(productDomain1.quantity())
                .pictureId(productDomain1.pictureId())
                .tags(productDomain1.tags())
                .build();

        final var pageDomain = new PageImpl<>(List.of(
                productDomain0,
                productDomain1
                ));
        final var pageResponse = new PageImpl<>(List.of(
                productRes0,
                productRes1
        ));

        final var pageIndex = 0;
        final var pageSize = 3;
        final var pageReq = PageRequest.of(pageIndex, pageSize);


        // Given
        when(productInput.retrieveByPage(pageReq))
                .thenReturn(pageDomain);

        when(productRestMapper.toProductDetailResponse(productDomain0))
                .thenReturn(productRes0);
        when(productRestMapper.toProductDetailResponse(productDomain1))
                .thenReturn(productRes1);

        // When
        final var pageRetrieved = productRestAdapter.getProductByPage(pageIndex, pageSize);

        // Then
        assertThat(pageRetrieved).isNotNull();
        assertThat(pageRetrieved).isEqualTo(pageResponse);

        verify(productInput).retrieveByPage(pageReq);
        verify(productRestMapper).toProductDetailResponse(productDomain0);
        verify(productRestMapper).toProductDetailResponse(productDomain1);
    }

    @Test
    void getProductById_when_nominal() {
        final var productDomain = ProductSampleTest.domain();
        final var productDetailResponseWanted = ProductDetalResponse.builder()
                .id(productDomain.id())
                .name(productDomain.name())
                .quantity(productDomain.quantity())
                .build();

        // Given
        when(productInput.retrieveById(productDomain.id()))
                .thenReturn(productDomain);
        when(productRestMapper.toProductDetailResponse(productDomain))
                .thenReturn(productDetailResponseWanted);

        // When
        final var productDetailResponse = productRestAdapter.getProductById(productDomain.id().toString());

        // Then
        assertThat(productDetailResponse).isNotNull();
        assertThat(productDetailResponse).isEqualTo(productDetailResponseWanted);

        verify(productInput).retrieveById(productDomain.id());
        verify(productRestMapper).toProductDetailResponse(productDomain);
    }

    @Test
    void createProduct_when_nominal() {
        final var productCreateRequest = ProductCreateRequest.builder()
                .name("CreatedProduct")
                .quantity(20)
                .build();
        final var productReq = Product.builder()
                .id(null)
                .name(productCreateRequest.name())
                .quantity(productCreateRequest.quantity())
                .build();
        final var productRes = productReq.toBuilder()
                .id(UUID.randomUUID())
                .build();
        final var productResponseRest = ProductResponse.builder()
                .id(productRes.id())
                .name(productRes.name())
                .build();

        // Given
        when(productRestMapper.toProduct(productCreateRequest))
                .thenReturn(productReq);
        when(productInput.addProduct(productReq))
                .thenReturn(productRes);
        when(productRestMapper.toProductResponse(productRes))
                .thenReturn(productResponseRest);
        // When
        final var productResponse = productRestAdapter.createProduct(productCreateRequest);

        // Then
        assertThat(productResponse).isNotNull();
        assertThat(productResponse).isEqualTo(productResponseRest);

        verify(productRestMapper).toProduct(productCreateRequest);
        verify(productRestMapper).toProductResponse(productRes);

        verify(productInput).addProduct(productReq);

    }

}