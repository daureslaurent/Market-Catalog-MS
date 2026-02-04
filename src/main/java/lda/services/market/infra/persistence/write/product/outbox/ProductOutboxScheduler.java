package lda.services.market.infra.persistence.write.product.outbox;

import com.lda.streambox.json.JsonConverter;
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

    private final JsonConverter jsonConverter;
    private final FakeKafkaContainer fakeKafkaContainer;

    @Scheduled(fixedRate = 5000)
    public void consumeOutbox() {
        outboxInput.lockNextBatch(100)
                .forEach(outbox -> {
                            log.info("Produce event {}", outbox.getId());
                            // Send event to kafka

                            // Test Impl
                            final var jsonKafka = jsonConverter.toJson(outbox);
                            log.info("Faking kafka ... {}", jsonKafka);
                            fakeKafkaContainer.addJson(jsonKafka);

                            // Delete event from outbox
                            outboxInput.finish(outbox);
                            log.info("Finishing outbox event {}", outbox.getId());
                        }
                );
    }

}
