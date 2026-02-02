package lda.services.market.infra.persistence.product.read.mapper;

import lda.services.market.domain.product.ProductSampleTest;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ProductReadPersistenceMapperTest {

    private final ProductReadPersistenceMapper productReadPersistenceMapper =
            Mappers.getMapper(ProductReadPersistenceMapper.class);

    @Test
    void toDomain() {

        // Given
        final var entity = ProductSampleTest.entityRead();
        final var domain = ProductSampleTest.domain(entity.getId());

        // When
        final var domainReturned = productReadPersistenceMapper.toDomain(entity);

        // Then
        assertThat(domainReturned).isNotNull();
        assertThat(domainReturned).isEqualTo(domain);
    }

    @Test
    void toEntity() {

        // Given
        final var entity = ProductSampleTest.entityRead();
        final var domain = ProductSampleTest.domain(entity.getId());

        // When
        final var entityReturned = productReadPersistenceMapper.toEntity(domain);

        // Then
        assertThat(entityReturned).isNotNull();
        assertThat(entityReturned).isEqualTo(entity);
    }
}