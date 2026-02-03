package lda.services.market.infra.persistence.product.read.inbox;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("java:S1602")
public class ProductInboxScheduler {

    private final ProductInboxAdapter inboxInput;

    @Scheduled(fixedRate = 7000)
    public void consumeOutbox() {
        inboxInput.lockNextBatch(100)
                .forEach(outbox -> {
                            log.info("Consuming event {}", outbox.getId());
                            inboxInput.createProductEvent(outbox);
                            inboxInput.finish(outbox);
                        }
                );
    }

}
