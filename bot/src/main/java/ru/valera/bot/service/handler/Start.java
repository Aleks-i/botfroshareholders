package ru.valera.bot.service.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.valera.bot.bot.Bot;
import ru.valera.bot.model.Content;
import ru.valera.bot.model.chat.Chat;
import ru.valera.bot.model.chat.ChatType;
import ru.valera.bot.repository.ChatRepository;
import ru.valera.bot.service.keyboard.MainKeyboard;
import ru.valera.bot.to.ChatTo;
import ru.valera.bot.to.MessageType;
import ru.valera.bot.to.UpdateTO;
import ru.valera.bot.util.MessageUtil;
import ru.valera.bot.util.WordsUtil;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class Start implements Handler {

    @Autowired
    private final MainKeyboard keyboard;
    @Autowired
    private ChatRepository chatRepository;

    @Override
    public Content handle(UpdateTO updateTO) {
        long chatId = updateTO.getChatId();
        if (!Bot.CHAT_STORAGE.containsKey(chatId)) {
            ChatType chatType = updateTO.getChatType();

            ChatTo chat = new ChatTo(chatId, chatType);
            Bot.CHAT_STORAGE.put(chatId, chat);

            chatRepository.save(new Chat(chatId, chatType));
        }
        return MessageUtil.getContent(WordsUtil.MESSAGE_BOT_DESCRIPTION, updateTO, keyboard);
    }

    @Override
    public Set<MessageType> getMessageType() {
        return Set.of(MessageType.START);
    }
}
