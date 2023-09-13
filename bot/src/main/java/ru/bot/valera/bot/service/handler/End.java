package ru.bot.valera.bot.service.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.repository.ChatRepository;
import ru.bot.valera.bot.to.UpdateTO;

import java.util.Set;

import static ru.bot.valera.bot.bot.Bot.CHAT_STORAGE;
import static ru.bot.valera.bot.util.WordsUtil.MESSAGE_BOT_DELETE;

@Component
@RequiredArgsConstructor
public class End extends AbstractContent {

    private final ChatRepository chatRepository;

    @Override
    public Content handle(UpdateTO updateTO) {
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
