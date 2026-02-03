package lda.services.market.infra.persistence.product.read.inbox.entity;

import com.lda.streambox.entity.StreamBoxBasePayloadEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@RequiredArgsConstructor
@Data
@SuperBuilder
@Entity(name = "PRODUCT_INBOX")
@Table(name = "PRODUCT_INBOX")
public class ProductInboxEventEntity extends StreamBoxBasePayloadEntity {
    @Column(
            nullable = false,
            unique = true
    )
    private UUID refOutbox;
}
