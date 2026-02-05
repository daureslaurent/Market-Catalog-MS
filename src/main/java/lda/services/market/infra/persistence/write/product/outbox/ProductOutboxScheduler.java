package lda.services.market.infra.persistence.write.product.outbox;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("java:S1602")
public class ProductOutboxScheduler {

    private final ProductOutboxAdapter outboxInput;

    @Scheduled(fixedRate = 5000)
    public void consumeOutbox() {
        outboxInput.lockNextBatch(100)
                .forEach(outboxInput::handleEvent);
    }

}
