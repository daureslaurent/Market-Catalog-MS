package lda.services.market.infra.config;

import lda.services.market.infra.persistence.write.product.outbox.ProductOutboxAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class StreamBoxConfig {

//    @Bean
//    public ProductOutboxAdapter  productOutboxAdapter() {
//        return new ProductOutboxAdapter();
//    }

}
