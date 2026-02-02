package lda.services.market.infra.persistence;

import lda.services.market.domain.product.port.ProductReadOutput;
import lda.services.market.domain.product.port.ProductWriteOutput;
import lda.services.market.domain.product.service.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    ProductService productService(
            ProductReadOutput productReadOutput,
            ProductWriteOutput productWriteOutput) {
        return new ProductService(productReadOutput, productWriteOutput);
    }

}
