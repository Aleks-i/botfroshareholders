package ru.bot.valera.bot.service.handlers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.model.persist.chat.Chat;
import ru.bot.valera.bot.model.persist.chat.ChatType;
import ru.bot.valera.bot.repository.ChatRepository;
import ru.bot.valera.bot.service.TelegramApi;
import ru.bot.valera.bot.service.keyboards.MainKeyboard;
import ru.bot.valera.bot.to.UpdateTo;
import ru.bot.valera.bot.util.WordsUtil;

import java.util.Map;
import java.util.Set;

import static ru.bot.valera.bot.bot.Bot.CHAT_STORAGE;
import static ru.bot.valera.bot.model.Command.*;
import static ru.bot.valera.bot.model.persist.chat.ChatType.GROUP;
import static ru.bot.valera.bot.model.persist.chat.ChatType.SUPERGROUP;
import static ru.bot.valera.bot.util.Util.initContentCounter;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Start extends AbstractContent {

    final MainKeyboard keyboard;
    final ChatRepository chatRepository;
    final TelegramApi telegramApi;

    @Override
    public Content handle(UpdateTo updateTo) {
        long chatId = updateTo.getChatId();
        ChatType chatType = updateTo.getChatType();

        if (!CHAT_STORAGE.containsKey(chatId)) {
            if (chatType == GROUP || chatType == SUPERGROUP) {
                if (telegramApi.isGroupAdmin(chatId, updateTo.getUserIdFrom())) {
                    return startBot(updateTo);
                } else {
                    return getContent("*" + updateTo.getUserName() + "*" +
                            " извини дорогой, но в группах стартовать бота могут только админы", updateTo, keyboard);
                }
            } else {
                return startBot(updateTo);
            }
        }
        return getContent("Держи доп клаву друг", updateTo, keyboard);
    }

    private Content startBot(UpdateTo updateTo) {
        long chatId = updateTo.getChatId();

        Chat chat = new Chat(chatId, updateTo.getUserName(), updateTo.getChatType(),
                updateTo.getFirstName(), updateTo.getLastName());

        setDefaultSettings(chat);
        CHAT_STORAGE.put(chatId, chat);
        chatRepository.save(chat);

        initContentCounter(chatId);
        return getContent(WordsUtil.MESSAGE_BOT_DESCRIPTION, updateTo, keyboard);
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
