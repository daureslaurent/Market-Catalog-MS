package lda.services.market.application.api.rest.product;

import lda.services.market.application.api.rest.product.mapper.ProductRestMapper;
import lda.services.market.application.api.rest.product.model.ProductChangeQuantityRequest;
import lda.services.market.application.api.rest.product.model.ProductCreateRequest;
import lda.services.market.application.api.rest.product.model.ProductDetalResponse;
import lda.services.market.application.api.rest.product.model.ProductResponse;
import lda.services.market.domain.product.exception.ProductNotFoundException;
import lda.services.market.domain.product.port.ProductCommandInput;
import lda.services.market.domain.product.port.ProductQueryInput;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductRestAdapter {

    private final ProductQueryInput productQueryInput;
    private final ProductCommandInput productCommandInput;
    private final ProductRestMapper mapper;

    @PostMapping
    public ProductResponse createProduct(@RequestBody ProductCreateRequest productCreateRequest) {
        final var product = mapper.toProduct(productCreateRequest);
        return mapper.toProductResponse(
                productCommandInput.addProduct(product)
        );
    }

    @GetMapping
    public Page<ProductDetalResponse> getProductByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        final var pageRequest = PageRequest.of(page, size);
        return productQueryInput.retrieveByPage(pageRequest)
                .map(mapper::toProductDetailResponse);
    }

    @GetMapping("/{id}")
    public ProductDetalResponse getProductById(@PathVariable final String id) {
        final var uuid = UUID.fromString(id);
        return mapper.toProductDetailResponse(
                productQueryInput.retrieveById(uuid)
        );
    }

    @PutMapping("/{id}/quantity")
    public ProductDetalResponse updateQuantityProductById(
            @PathVariable final String id,
            @RequestBody ProductChangeQuantityRequest quantityRequest
    ) {
        final var uuid = UUID.fromString(id);
        return mapper.toProductDetailResponse(
                productCommandInput.updateQuantity(uuid, quantityRequest.quantity())
        );
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFound(ProductNotFoundException ex) {
        return Map.of("message", ex.getMessage());
    }

}
