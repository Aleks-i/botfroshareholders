package ru.bot.valera.bot.service.handlers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.model.persist.chat.ChatType;
import ru.bot.valera.bot.repository.ChatRepository;
import ru.bot.valera.bot.service.TelegramApi;
import ru.bot.valera.bot.to.UpdateTo;

import java.util.Set;

import static ru.bot.valera.bot.bot.Bot.CHAT_STORAGE;
import static ru.bot.valera.bot.model.persist.chat.ChatType.GROUP;
import static ru.bot.valera.bot.model.persist.chat.ChatType.SUPERGROUP;
import static ru.bot.valera.bot.util.WordsUtil.MESSAGE_BOT_DELETE;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class End extends AbstractContent {

    final ChatRepository chatRepository;
    final TelegramApi telegramApi;

    @Override
    public Content handle(UpdateTo updateTo) {
        ChatType chatType = updateTo.getChatType();
        if (chatType == GROUP || chatType == SUPERGROUP) {
            if (telegramApi.isGroupAdmin(updateTo.getChatId(), updateTo.getUserIdFrom())) {
                return stopBot(updateTo);
            } else {
                return getContent("*" + updateTo.getUserName() + "*" +
                        " извини дорогой, но в группах тормозить бота могут только админы.", updateTo);
            }
        } else {
            return stopBot(updateTo);
        }
    }

    private Content stopBot(UpdateTo updateTO) {
        long chatId = updateTO.getChatId();
        chatRepository.deleteById(CHAT_STORAGE.get(chatId).getId());
        CHAT_STORAGE.remove(chatId);

        return getContent(MESSAGE_BOT_DELETE, updateTO);
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(Command.END);
    }
}
