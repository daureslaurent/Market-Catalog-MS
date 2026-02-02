package lda.services.market.infra.persistence;

import lda.services.market.domain.product.port.ProductOutput;
import lda.services.market.domain.product.service.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    ProductService productService(ProductOutput productOutput) {
        return new ProductService(productOutput);
    }

}
