package lda.services.market.infra.persistence.product.write.outbox;

import lda.services.market.infra.persistence.streambox.StreamBoxInput;
import lda.services.market.infra.persistence.product.write.outbox.entity.ProductOutboxEventEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("java:S1602")
public class ProductOutboxScheduler {

    private final StreamBoxInput<ProductOutboxEventEntity> outboxInput;

    @Scheduled(fixedRate = 5000)
    public void consumeOutbox() {
        log.info("Consuming outbox");
        outboxInput.lockNextBatch(100)
                .forEach(outbox -> {
                            // Send event to kafka

                            // Delete event from outbox
                            outboxInput.finish(outbox);
                        }
                );
    }

}
