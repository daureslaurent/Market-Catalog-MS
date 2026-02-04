package lda.services.market.infra.persistence.write.product.outbox.mapper;

import com.lda.streambox.json.JsonConverter;
import com.lda.streambox.model.StreamBoxEvent;
import lda.services.market.infra.persistence.write.product.outbox.entity.ProductOutboxEventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = JsonConverter.class)
public interface ProductOutboxMapper {

    @Mapping(target = "payload", expression = "java(jsonConverter.toJson(event.payload()))")
    ProductOutboxEventEntity toEntity(StreamBoxEvent<?> event);

}
