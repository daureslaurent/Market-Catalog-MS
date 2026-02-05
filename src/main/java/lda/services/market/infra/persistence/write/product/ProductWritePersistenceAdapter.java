package lda.services.market.infra.persistence.write.product;

import lda.services.market.domain.product.exception.ProductNotFoundException;
import lda.services.market.domain.product.model.Product;
import lda.services.market.domain.product.port.ProductWriteOutput;
import lda.services.market.infra.persistence.projection.ProductChangeQuantityEvent;
import lda.services.market.infra.persistence.write.product.outbox.ProductOutboxAdapter;
import lda.services.market.infra.persistence.write.product.mapper.ProductWritePersistenceMapper;
import lda.services.market.infra.persistence.write.product.repository.ProductWriteRepository;
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

    private final ProductOutboxAdapter outboxAdapter;

    @Override
    @Transactional(readOnly = true, transactionManager = "writeTransactionManager")
    public Optional<Product> getById(UUID id) {
        return productWriteRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Product create(Product product) {
        final var entity = mapper.toEntity(product);

        final var saved = productWriteRepository.save(entity);
        final var savedDomain = mapper.toDomain(saved);

        outboxAdapter.createProductEvent(savedDomain);

        return savedDomain;
    }

    @Override
    public Product changeQuantity(UUID idProduct, Integer value) {
        final var product = productWriteRepository.findById(idProduct)
                .orElseThrow(() -> new ProductNotFoundException(idProduct));

        final var change = value - product.getQuantity();
        final var isIncrease = change > 0;

        product.setQuantity(value);
        final var entitySaved = productWriteRepository.save(product);
        outboxAdapter.addEvent(ProductChangeQuantityEvent.builder()
                .idProduct(idProduct)
                .isIncrease(isIncrease)
                .changeValue(change)
                .build());

        return mapper.toDomain(entitySaved);
    }
}
