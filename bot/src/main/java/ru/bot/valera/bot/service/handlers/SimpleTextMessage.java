package ru.bot.valera.bot.service.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.service.keyboards.MainKeyboard;
import ru.bot.valera.bot.to.UpdateTo;

import java.util.Set;

import static ru.bot.valera.bot.model.Command.*;
import static ru.bot.valera.bot.util.WordsUtil.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class SimpleTextMessage extends AbstractContent {

    private final MainKeyboard keyboard;

    @Override
    public Content handle(UpdateTo updateTo) {
        log.debug("get message template for message {}", updateTo.getMessage());
        Command command = updateTo.getCommand();

        switch (command) {
            case GREETING -> {
                return getContent(getRandomElementFromList(GREETINGS), updateTo);
            }
            case LEFT_USER -> {
                return getContent(getRandomElementFromList(COMENT_LEFT_USER), updateTo);
            }
            case NEW_USER -> {
                return getContent(getRandomElementFromList(GREETINGS_NEW_USER), updateTo);
            }
            case VALERA -> {
                return getContent(getRandomElementFromList(ANSWER_FOR_VALERA), updateTo, keyboard);
            }
            case HELP -> {
                return getContent(MESSAGE_BOT_HELP, updateTo);
            }
            case STICKER -> {
                return getContent("StickerID: " + updateTo.getSticker().toString(), updateTo);
            }
            default -> {
                log.warn("Cant detect type of command {} in SimpleTextMessage handler", command);
                return getContent("", updateTo);
            }
        }
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(GREETING, VALERA, STICKER,
                HELP, NEW_USER, LEFT_USER);
    }
}
