package ru.bot.valera.bot.service.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.model.persist.chat.Chat;
import ru.bot.valera.bot.model.persist.chat.ChatType;
import ru.bot.valera.bot.repository.ChatRepository;
import ru.bot.valera.bot.service.keyboards.MainKeyboard;
import ru.bot.valera.bot.to.UpdateTO;
import ru.bot.valera.bot.util.WordsUtil;

import java.util.Map;
import java.util.Set;

import static ru.bot.valera.bot.bot.Bot.CHAT_STORAGE;
import static ru.bot.valera.bot.model.Command.*;
import static ru.bot.valera.bot.util.Util.initContentCounter;

@Component
@RequiredArgsConstructor
public class Start extends AbstractContent {

    private final MainKeyboard keyboard;
    private final ChatRepository chatRepository;

    @Override
    public Content handle(UpdateTO updateTO) {
        long chatId = updateTO.getChatId();
        if (!CHAT_STORAGE.containsKey(chatId)) {
            ChatType chatType = updateTO.getChatType();
            Chat chat = new Chat(chatId, chatType);

            setDefaultSettings(chat);
            CHAT_STORAGE.put(chatId, chat);
            chatRepository.save(chat);

            initContentCounter(chatId);
        }
        return getContent(WordsUtil.MESSAGE_BOT_DESCRIPTION, updateTO, keyboard);
    }

    private void setDefaultSettings(Chat chat) {
        Map<Command, Boolean> mailerMap = chat.getMailerMap();
        mailerMap.put(GIRLS, false);
        mailerMap.put(TITS_GIF, false);
        mailerMap.put(TITS_VIDEO, false);
        mailerMap.put(MANS, false);
        mailerMap.put(MANS_VIDEO, false);
        mailerMap.put(MANS_GIF, false);
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(Command.START);
    }
}
