package lda.services.market.infra.persistence.product.write.outbox.entity;

import com.lda.streambox.entity.StreamBoxBasePayloadEntity;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
@Entity(name = "PRODUCT_OUTBOX")
@Table(name = "PRODUCT_OUTBOX")
public class ProductOutboxEventEntity extends StreamBoxBasePayloadEntity {
}
