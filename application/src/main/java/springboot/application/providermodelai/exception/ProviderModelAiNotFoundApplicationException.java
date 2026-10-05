package springboot.application.providermodelai.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class ProviderModelAiNotFoundApplicationException extends NotFoundApplicationException {
    public ProviderModelAiNotFoundApplicationException(String id) {
        super("ProviderModelAi", id);
    }
}
