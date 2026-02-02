package lda.services.market.infra.persistence.product.write;

import lda.services.market.domain.product.model.Product;
import lda.services.market.domain.product.port.ProductWriteOutput;
import lda.services.market.infra.persistence.product.write.mapper.ProductWritePersistenceMapper;
import lda.services.market.infra.persistence.product.write.repository.ProductWriteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Component
public class ProductWritePersistenceAdapter implements ProductWriteOutput {

    private final ProductWriteRepository productWriteRepository;
    private final ProductWritePersistenceMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> getById(UUID id) {
        return productWriteRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Product save(Product product) {
        final var entity = mapper.toEntity(product);
        return mapper.toDomain(
                productWriteRepository.save(entity)
        );
    }
}
