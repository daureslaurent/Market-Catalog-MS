package lda.services.market.domain.product.exception;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(UUID idProduct) {
        super(String.format("Product %s not found",
                idProduct != null
                ? idProduct.toString()
                : "(null value)")
        );
    }
}
