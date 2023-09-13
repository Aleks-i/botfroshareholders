package ru.bot.valera.bot.to;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.persist.chat.ChatType;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateTO {
    Command command;
    Long chatId;
    int messageId;
    String[] callBackData;
    String textMessage;
    Message message;
    Sticker sticker;
    ChatType chatType;
    long userIdFrom;
    String errorMessage;

    public UpdateTO(Command command) {
        this.command = command;
    }

    public UpdateTO(Command command, Long chatId) {
        this.command = command;
        this.chatId = chatId;
    }
}
