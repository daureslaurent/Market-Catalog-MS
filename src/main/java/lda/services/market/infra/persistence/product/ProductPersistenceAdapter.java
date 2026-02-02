package lda.services.market.infra.persistence.product;

import lda.services.market.domain.product.model.Product;
import lda.services.market.domain.product.port.ProductOutput;
import lda.services.market.infra.persistence.product.mapper.ProductPersistenceMapper;
import lda.services.market.infra.persistence.product.mapper.TagPersistenceMapper;
import lda.services.market.infra.persistence.product.repository.ProductRepository;
import lda.services.market.infra.persistence.product.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Component
public class ProductPersistenceAdapter implements ProductOutput {

    private final ProductRepository productRepository;
    private final ProductPersistenceMapper productMapper;

    // Tag
    private final TagRepository tagRepository;
    private final TagPersistenceMapper tagMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<Product> getByPage(Pageable page) {
        return productRepository.findAll(page)
                .map(productMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> getById(UUID id) {
        return productRepository.findById(id)
                .map(productMapper::toDomain);
    }

    @Override
    public Product save(Product product) {
        final var entity = productMapper.toEntity(product);
        return productMapper.toDomain(
                productRepository.save(entity)
        );
    }
}
