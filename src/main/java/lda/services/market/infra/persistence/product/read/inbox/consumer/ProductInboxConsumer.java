package lda.services.market.infra.persistence.product.read.inbox.consumer;

import com.lda.streambox.json.JsonConverter;
import lda.services.market.infra.persistence.product.read.inbox.ProductInboxAdapter;
import lda.services.market.infra.persistence.product.read.inbox.entity.ProductInboxEventEntity;
import lda.services.market.infra.persistence.product.write.outbox.FakeKafkaContainer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class ProductInboxConsumer {

    private final ProductInboxAdapter productInboxAdapter;

    private final JsonConverter jsonConverter;
    private final FakeKafkaContainer fakeKafkaContainer;


    @Scheduled(fixedRate = 8000)
    void consume() {
        final var data = fakeKafkaContainer.getLastJson();
        if (data != null) {
            log.info("Faked consumer");
            final var inboxKafka = jsonConverter.fromJson(data, ProductInboxEventEntity.class);
            inboxKafka.setRefOutbox(inboxKafka.getId());
            inboxKafka.setId(null);
            productInboxAdapter.addToBox(inboxKafka);
        }
    }

}
