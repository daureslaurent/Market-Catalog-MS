package lda.services.market.domain.product;

import lda.services.market.domain.product.model.Product;
import lda.services.market.domain.product.model.Tag;
import lda.services.market.infra.persistence.product.read.entity.ProductReadEntity;
import lda.services.market.infra.persistence.product.write.entity.ProductWriteEntity;

import java.util.Set;
import java.util.UUID;

@SuppressWarnings("java:S2187")
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

//    public static Set<TagReadEntity> tagEntityRead() {
//        return Set.of(
//                TagReadEntity.builder()
//                        .id(TAG_0_ID)
//                        .name("Tag0")
//                        .build(),
//                TagReadEntity.builder()
//                        .id(TAG_1_ID)
//                        .name("Tag1")
//                        .build()
//        );
//    }
//
//    public static Set<TagReadEntity> tagEntityWrite() {
//        return Set.of(
//                TagReadEntity.builder()
//                        .id(TAG_0_ID)
//                        .name("Tag0")
//                        .build(),
//                TagReadEntity.builder()
//                        .id(TAG_1_ID)
//                        .name("Tag1")
//                        .build()
//        );
//    }

    public static Product domain() {
        return Product.builder()
                .id(UUID.randomUUID())
                .name("Sample product")
                .detail("Detail of product")
                .pictureId(PICTURE_ID)
                .quantity(20)
//                .tags(tagDomain())
                .build();
    }

    public static Product domain(final UUID id) {
        return Product.builder()
                .id(id)
                .name("Sample product")
                .detail("Detail of product")
                .pictureId(PICTURE_ID)
                .quantity(20)
//                .tags(tagDomain())
                .build();
    }

    public static ProductReadEntity entityRead() {
        final var domain = domain();
        return ProductReadEntity.builder()
                .id(domain.id())
                .name(domain.name())
                .detail(domain().detail())
                .pictureId(domain().pictureId())
                .quantity(domain.quantity())
//                .tags(tagEntityRead())
                .build();

    }

    public static ProductWriteEntity entityWrite() {
        final var domain = domain();
        return ProductWriteEntity.builder()
                .id(domain.id())
                .name(domain.name())
                .detail(domain().detail())
                .pictureId(domain().pictureId())
                .quantity(domain.quantity())
//                .tags(tagEntityWrite())
                .build();

    }

}
