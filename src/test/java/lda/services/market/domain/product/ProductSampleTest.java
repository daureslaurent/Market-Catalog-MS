package lda.services.market.domain.product;

import lda.services.market.domain.product.model.Product;
import lda.services.market.domain.product.model.Tag;
import lda.services.market.infra.persistence.product.entity.ProductEntity;
import lda.services.market.infra.persistence.product.entity.TagEntity;

import java.util.Set;
import java.util.UUID;

public class ProductSampleTest {

    private static final String PICTURE_ID = UUID.randomUUID().toString();
    private static final UUID TAG_0_ID = UUID.randomUUID();
    private static final UUID TAG_1_ID = UUID.randomUUID();

    public static Set<Tag> tagDomain() {
        return Set.of(
                Tag.builder()
                        .id(TAG_0_ID)
                        .name("Tag0")
                        .build(),
                Tag.builder()
                        .id(TAG_1_ID)
                        .name("Tag1")
                        .build()
        );
    }

    public static Set<TagEntity> tagEntity() {
        return Set.of(
                TagEntity.builder()
                        .id(TAG_0_ID)
                        .name("Tag0")
                        .build(),
                TagEntity.builder()
                        .id(TAG_1_ID)
                        .name("Tag1")
                        .build()
        );
    }

    public static Product domain() {
        return Product.builder()
                .id(UUID.randomUUID())
                .name("Sample product")
                .detail("Detail of product")
                .pictureId(PICTURE_ID)
                .quantity(20)
                .tags(tagDomain())
                .build();
    }

    public static Product domain(final UUID id) {
        return Product.builder()
                .id(id)
                .name("Sample product")
                .detail("Detail of product")
                .pictureId(PICTURE_ID)
                .quantity(20)
                .tags(tagDomain())
                .build();
    }

    public static ProductEntity entity() {
        final var domain = domain();
        return ProductEntity.builder()
                .id(domain.id())
                .name(domain.name())
                .detail(domain().detail())
                .pictureId(domain().pictureId())
                .quantity(domain.quantity())
                .tags(tagEntity())
                .build();

    }

}
