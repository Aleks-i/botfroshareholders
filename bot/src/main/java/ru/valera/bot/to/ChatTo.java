package ru.valera.bot.to;

import lombok.Data;
import ru.valera.bot.model.chat.Chat;
import ru.valera.bot.model.chat.ChatType;
import ru.valera.bot.model.chat.MailerType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Data
public class ChatTo {
    private final long chatId;

    private final ChatType chatType;

    private final Map<MailerType, Boolean> senderMap = new HashMap<>();

    public ChatTo(long chatId, ChatType chatType) {
        this.chatId = chatId;
        this.chatType = chatType;
        Arrays.stream(MailerType.values())
                .forEach(st -> senderMap.put(st, false));
    }

    public ChatTo(Chat ch) {
        this.chatId = ch.getChatId();
        this.chatType = ch.getChatType();
        senderMap.putAll(ch.getSenderMap());
    }
}
