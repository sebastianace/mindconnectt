package springboot.application.chatairun.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class ChatAiRunNotFoundApplicationException extends NotFoundApplicationException {
    public ChatAiRunNotFoundApplicationException(String id) {
        super("ChatAiRun", id);
    }
}
