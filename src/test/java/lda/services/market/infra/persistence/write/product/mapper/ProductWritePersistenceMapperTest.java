package lda.services.market.infra.persistence.write.product.mapper;

import lda.services.market.domain.product.ProductSampleTest;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ProductWritePersistenceMapperTest {

    private final ProductWritePersistenceMapper productWritePersistenceMapper =
            Mappers.getMapper(ProductWritePersistenceMapper.class);

    @Test
    void toDomain() {

        // Given
        final var entity = ProductSampleTest.entityWrite();
        final var domain = ProductSampleTest.domain(entity.getId());

        // When
        final var domainReturned = productWritePersistenceMapper.toDomain(entity);

        // Then
        assertThat(domainReturned).isNotNull();
        assertThat(domainReturned).isEqualTo(domain);
    }

    @Test
    void toEntity() {

        // Given
        final var entity = ProductSampleTest.entityWrite();
        final var domain = ProductSampleTest.domain(entity.getId());

        // When
        final var entityReturned = productWritePersistenceMapper.toEntity(domain);

        // Then
        assertThat(entityReturned).isNotNull();
        assertThat(entityReturned).isEqualTo(entity);
    }
}