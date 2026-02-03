package lda.services.market.infra.config;

import lda.services.market.domain.product.port.ProductReadOutput;
import lda.services.market.domain.product.port.ProductWriteOutput;
import lda.services.market.domain.product.service.ProductCommandService;
import lda.services.market.domain.product.service.ProductQueryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    ProductQueryService productQueryService(
            ProductReadOutput productReadOutput) {
        return new ProductQueryService(productReadOutput);
    }

    @Bean
    ProductCommandService productCommandService(
            ProductWriteOutput productWriteOutput) {
        return new ProductCommandService(productWriteOutput);
    }

}
