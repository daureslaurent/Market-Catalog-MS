package lda.services.market.infra.persistence.read.product;

import lda.services.market.domain.product.model.Product;
import lda.services.market.domain.product.port.ProductReadOutput;
import lda.services.market.infra.persistence.read.product.mapper.ProductReadPersistenceMapper;
import lda.services.market.infra.persistence.read.product.repository.ProductReadRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Component
public class ProductReadPersistenceAdapter implements ProductReadOutput {

    private final ProductReadRepository productReadRepository;
    private final ProductReadPersistenceMapper productMapper;

    // Tag
//    private final TagReadRepository tagReadRepository;
//    private final TagReadPersistenceMapper tagMapper;

    @Override
    @Transactional(
            transactionManager = "readTransactionManager",
            readOnly = true
    )
    public Page<Product> getByPage(Pageable page) {
        return productReadRepository.findAll(page)
                .map(productMapper::toDomain);
    }

    @Override
    @Transactional(
            transactionManager = "readTransactionManager",
            readOnly = true
    )
    public Optional<Product> getById(UUID id) {
        return productReadRepository.findById(id)
                .map(productMapper::toDomain);
    }

}
