package ru.bot.valera.bot.service.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.service.keyboards.MainKeyboard;
import ru.bot.valera.bot.to.UpdateTO;

import java.util.Set;

import static ru.bot.valera.bot.model.Command.*;
import static ru.bot.valera.bot.util.WordsUtil.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class SimpleTextMessage extends AbstractContent {

    private final MainKeyboard keyboard;

    @Override
    public Content handle(UpdateTO updateTO) {
        log.debug("get message template for message {}", updateTO.getMessage());
        Command command = updateTO.getCommand();

        switch (command) {
            case GREETING -> {
                return getContent(getRandomElementFromList(GREETINGS), updateTO);
            }
            case LEFT_USER -> {
                return getContent(getRandomElementFromList(COMENT_LEFT_USER), updateTO);
            }
            case NEW_USER -> {
                return getContent(getRandomElementFromList(GREETINGS_NEW_USER), updateTO);
            }
            case VALERA -> {
                return getContent(getRandomElementFromList(ANSWER_FOR_VALERA), updateTO, keyboard);
            }
            case HELP -> {
                return getContent(MESSAGE_BOT_DESCRIPTION, updateTO);
            }
            case STICKER -> {
                return getContent("StickerID: " + updateTO.getSticker().toString(), updateTO);
            }
            default -> {
                log.warn("Cant detect type of command {} in SimpleTextMessage handler", command);
                return getContent("", updateTO);
            }
        }
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(GREETING, VALERA, STICKER,
                HELP, NEW_USER, LEFT_USER);
    }
}
