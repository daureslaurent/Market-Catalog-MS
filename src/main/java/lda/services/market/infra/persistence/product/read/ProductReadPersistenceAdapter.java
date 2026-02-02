package lda.services.market.infra.persistence.product.read;

import lda.services.market.domain.product.model.Product;
import lda.services.market.domain.product.port.ProductReadOutput;
import lda.services.market.infra.persistence.product.read.mapper.ProductReadPersistenceMapper;
import lda.services.market.infra.persistence.product.read.mapper.TagReadPersistenceMapper;
import lda.services.market.infra.persistence.product.read.repository.ProductReadRepository;
import lda.services.market.infra.persistence.product.read.repository.TagReadRepository;
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
    private final TagReadRepository tagReadRepository;
    private final TagReadPersistenceMapper tagMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<Product> getByPage(Pageable page) {
        return productReadRepository.findAll(page)
                .map(productMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> getById(UUID id) {
        return productReadRepository.findById(id)
                .map(productMapper::toDomain);
    }

}
