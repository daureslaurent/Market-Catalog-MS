package lda.services.market.infra.persistence.streambox.json;

public class JsonConverterToJsonException extends RuntimeException {
    public JsonConverterToJsonException(String message, Throwable cause) {
        super(message, cause);
    }
}
