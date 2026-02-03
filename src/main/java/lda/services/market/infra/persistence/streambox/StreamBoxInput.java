package lda.services.market.infra.persistence.streambox;

import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StreamBoxInput<T> {

    List<T> lockNextBatch(@Param("limit") int limit);

    void finish(T outboxEvent);

}
