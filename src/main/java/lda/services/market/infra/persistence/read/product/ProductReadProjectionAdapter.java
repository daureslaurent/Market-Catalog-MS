package lda.services.market.infra.persistence.read.product;

import lda.services.market.domain.product.model.Product;
import lda.services.market.infra.persistence.read.product.mapper.ProductReadPersistenceMapper;
import lda.services.market.infra.persistence.read.product.repository.ProductReadRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@AllArgsConstructor
@Component
public class ProductReadProjectionAdapter {

    private final ProductReadRepository repository;
    private final ProductReadPersistenceMapper mapper;

    @Transactional(transactionManager = "readTransactionManager")
    public void upsert(Product product) {
        repository.save(mapper.toEntity(product));
    }

    @Transactional(transactionManager = "readTransactionManager")
    public void delete(UUID productId) {
        repository.deleteById(productId);
    }
}
