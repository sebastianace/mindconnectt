package springboot.application.chatconversationaisetting.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class ChatConversationAiSettingNotFoundApplicationException extends NotFoundApplicationException {
    public ChatConversationAiSettingNotFoundApplicationException(String id) {
        super("ChatConversationAiSetting", id);
    }
}
