package lda.services.market.domain.product.exception;

import java.util.UUID;

public class ProductQuantityTooSmallException extends RuntimeException {
    public ProductQuantityTooSmallException(UUID idProduct) {
        super(String.format("Product quantity (%s) too small",
                idProduct != null
                ? idProduct.toString()
                : "(null value)")
        );
    }
}
