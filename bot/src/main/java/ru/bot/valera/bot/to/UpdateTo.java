package ru.bot.valera.bot.to;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.SourceMessageType;
import ru.bot.valera.bot.model.persist.chat.ChatType;

import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateTo {
    Command command;
    SourceMessageType sourceMessageType;
    Message message;
    Set<Long> chatIdsSet;
    ChatType chatType;
    long chatId;
    String userName;
    String firstName;
    String lastName;
    long userIdFrom;
    int messageId;
    String[] callBackData;
    String textMessage;
    Sticker sticker;
    String errorMessage;

    public UpdateTo(Command command) {
        this.command = command;
    }

    public UpdateTo(SourceMessageType sourceMessageType, Command command, Long chatId) {
        this.sourceMessageType = sourceMessageType;
        this.command = command;
        this.chatId = chatId;
    }

    public UpdateTo(SourceMessageType sourceMessageType, Command command, Long chatId, int messageId) {
        this.sourceMessageType = sourceMessageType;
        this.command = command;
        this.chatId = chatId;
        this.messageId = messageId;
    }

    public UpdateTo(SourceMessageType sourceMessageType, Command command) {
        this.sourceMessageType = sourceMessageType;
        this.command = command;
    }
}
