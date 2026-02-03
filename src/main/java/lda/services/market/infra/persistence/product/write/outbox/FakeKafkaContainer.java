package lda.services.market.infra.persistence.product.write.outbox;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class FakeKafkaContainer {

    private final List<String> container = new ArrayList<>();

    public void addJson(String json) {
        container.add(json);
    }

    public String getLastJson() {
        if (container.isEmpty()) {
            return null;
        }
        return container.removeLast();
    }

}
