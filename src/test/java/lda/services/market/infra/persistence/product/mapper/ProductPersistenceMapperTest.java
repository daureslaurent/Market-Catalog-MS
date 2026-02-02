package lda.services.market.infra.persistence.product.mapper;

import lda.services.market.domain.product.ProductSampleTest;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ProductPersistenceMapperTest {

    private final ProductPersistenceMapper productPersistenceMapper =
            Mappers.getMapper(ProductPersistenceMapper.class);

    @Test
    void toDomain() {

        // Given
        final var entity = ProductSampleTest.entity();
        final var domain = ProductSampleTest.domain(entity.getId());

        // When
        final var domainReturned = productPersistenceMapper.toDomain(entity);

        // Then
        assertThat(domainReturned).isNotNull();
        assertThat(domainReturned).isEqualTo(domain);
    }

    @Test
    void toEntity() {

        // Given
        final var entity = ProductSampleTest.entity();
        final var domain = ProductSampleTest.domain(entity.getId());

        // When
        final var entityReturned = productPersistenceMapper.toEntity(domain);

        // Then
        assertThat(entityReturned).isNotNull();
        assertThat(entityReturned).isEqualTo(entity);
    }
}