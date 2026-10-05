package springboot.application.chatairunmetric.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class ChatAiRunMetricNotFoundApplicationException extends NotFoundApplicationException {
    public ChatAiRunMetricNotFoundApplicationException(String id) {
        super("ChatAiRunMetric", id);
    }
}
