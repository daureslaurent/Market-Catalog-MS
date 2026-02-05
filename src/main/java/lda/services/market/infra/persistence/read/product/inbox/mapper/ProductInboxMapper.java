package lda.services.market.infra.persistence.read.product.inbox.mapper;

import com.lda.streambox.json.JsonConverter;
import com.lda.streambox.model.StreamBoxEvent;
import lda.services.market.infra.persistence.projection.product.ProductCreateEvent;
import lda.services.market.infra.persistence.read.product.inbox.entity.ProductInboxEventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = JsonConverter.class)
public interface ProductInboxMapper {

    @Mapping(target = "payload", expression = "java(jsonConverter.fromJson(entity.getPayload(), ProductCreateEvent.class))")
    StreamBoxEvent<ProductCreateEvent> toCreateEvent(ProductInboxEventEntity entity);

    @Mapping(target = "payload", expression = "java(jsonConverter.toJson(event.payload()))")
    ProductInboxEventEntity toEntity(StreamBoxEvent<?> event);

}
