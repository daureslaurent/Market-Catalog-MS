package lda.services.market.infra.persistence.streambox.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@NoRepositoryBean
public interface StreamBoxRepository<T> extends StreamBoxQueryRepository<T>, CrudRepository<T, UUID> {
}
