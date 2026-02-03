package lda.services.market.infra.persistence.streambox;

import org.mapstruct.Mapping;

public interface StreamBoxMapper<T> {

    @Mapping(target = "payload", expression = "java(jsonConverter.toJson(event.payload()))")
    T toEntity(StreamBoxEvent<?> event);

    @Mapping(target = "payload", expression = "java(jsonConverter.fromJson(entity.getPayload(), Object.class))")
    StreamBoxEvent<Object> toDomain(T entity);

}
