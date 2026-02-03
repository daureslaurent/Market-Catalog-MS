package lda.services.market.infra.json;

public class JsonConverterToJsonException extends RuntimeException {
    public JsonConverterToJsonException(String message, Throwable cause) {
        super(message, cause);
    }
}
