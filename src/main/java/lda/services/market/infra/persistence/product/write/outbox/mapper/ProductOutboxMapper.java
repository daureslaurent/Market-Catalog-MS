package lda.services.market.infra.persistence.product.write.outbox.mapper;

import lda.services.market.infra.persistence.streambox.StreamBoxMapper;
import lda.services.market.infra.persistence.streambox.json.JsonConverter;
import lda.services.market.infra.persistence.product.write.outbox.entity.ProductOutboxEventEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = JsonConverter.class)
public interface ProductOutboxMapper extends StreamBoxMapper<ProductOutboxEventEntity> {
}
