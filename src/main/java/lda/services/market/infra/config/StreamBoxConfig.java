package lda.services.market.infra.config;

import com.lda.streambox.anotation.StreamBox;
import com.lda.streambox.anotation.StreamBoxType;
import com.lda.streambox.json.JsonConverter;
import com.lda.streambox.repository.StreamBoxRepository;
import lda.services.market.infra.persistence.read.product.ProductProjectionAdapter;
import lda.services.market.infra.persistence.read.product.inbox.InboxFactory;
import lda.services.market.infra.persistence.read.product.inbox.ProductInboxAdapter;
import lda.services.market.infra.persistence.read.product.inbox.entity.ProductInboxEventEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@StreamBox(
        type = StreamBoxType.INBOX,
        scanBasePackages = {"lda.services.market.infra.persistence.projection"}
)
public class StreamBoxConfig {

    @Bean
    public ProductInboxAdapter productInboxAdapter(
            JsonConverter jsonConverter,
            StreamBoxRepository<ProductInboxEventEntity> streamBoxRepository,
            InboxFactory streamBoxFactory,
            ProductProjectionAdapter projectionAdapter
    ) {
        return new ProductInboxAdapter(jsonConverter, streamBoxRepository, streamBoxFactory, projectionAdapter);
    }

}
