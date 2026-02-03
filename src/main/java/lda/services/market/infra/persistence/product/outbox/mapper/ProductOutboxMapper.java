package lda.services.market.infra.persistence.product.outbox.mapper;

import lda.services.market.infra.json.JsonConverter;
import lda.services.market.infra.persistence.product.outbox.entity.ProductOutboxEventEntity;
import lda.services.market.infra.persistence.product.outbox.model.ProductOutboxEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = JsonConverter.class)
public interface ProductOutboxMapper {

    @Mapping(target = "payload", expression = "java(jsonConverter.toJson(event.payload()))")
    ProductOutboxEventEntity toEntity(ProductOutboxEvent<?> event);

    @Mapping(target = "payload", expression = "java(jsonConverter.fromJson(entity.getPayload(), Object.class))")
    ProductOutboxEvent<Object> toDomain(ProductOutboxEventEntity entity);

}
