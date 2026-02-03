package lda.services.market.infra.persistence.product.write.outbox.entity;

import jakarta.persistence.*;
import lda.services.market.infra.persistence.streambox.entity.StreamBoxBasePayloadEntity;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
@Entity(name = "PRODUCT_OUTBOX")
@Table(name = "PRODUCT_OUTBOX")
public class ProductOutboxEventEntity extends StreamBoxBasePayloadEntity {
}
