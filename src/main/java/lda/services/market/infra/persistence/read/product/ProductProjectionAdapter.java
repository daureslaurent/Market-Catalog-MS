package lda.services.market.infra.persistence.read.product;

import lda.services.market.domain.product.model.Product;
import lda.services.market.infra.persistence.projection.product.ProductChangeQuantityEvent;
import lda.services.market.infra.persistence.projection.product.ProductCreateEvent;
import lda.services.market.infra.persistence.read.product.entity.ProductReadEntity;
import lda.services.market.infra.persistence.read.product.mapper.ProductReadPersistenceMapper;
import lda.services.market.infra.persistence.read.product.repository.ProductReadRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Component
public class ProductProjectionAdapter {

    private final ProductReadRepository repository;
    private final ProductReadPersistenceMapper mapper;

    public void upsert(Product product) {
        repository.save(mapper.toEntity(product));
    }

    public void upsert(ProductReadEntity entity) {
        repository.save(entity);
    }

    public Optional<ProductReadEntity> get(UUID id) {
        return repository.findById(id);
    }

    public void delete(UUID productId) {
        repository.deleteById(productId);
    }


    public void createProductProjection(final ProductCreateEvent event) {
        upsert(event.product());
    }

    public void changeQuantityProjection(final ProductChangeQuantityEvent event) {
        final var projection = get(event.idProduct())
                .orElseThrow(() -> new RuntimeException("No projection found for product " + event.idProduct()));
        final var change = event.changeValue();
        final var quantity = projection.getQuantity() + (event.isIncrease()
                ? change
                : -change);

        projection.setQuantity(quantity);
        upsert(projection);
    }
}
