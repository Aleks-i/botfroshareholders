package ru.valera.bot.to;

import lombok.Data;
import ru.valera.bot.model.chat.ChatType;

@Data
public class UpdateTO {
    private MessageType messageType;
    private Long chatId;
    private int messageId;
    private String[] callBackData;
    private String message;
    private ChatType chatType;

    public UpdateTO(MessageType messageType) {
        this.messageType = messageType;
    }

    public UpdateTO(MessageType messageType, Long chatId) {
        this.messageType = messageType;
        this.chatId = chatId;
    }
}
