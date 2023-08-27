package ru.valera.bot.service.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.valera.bot.model.Content;
import ru.valera.bot.service.keyboard.MainKeyboard;
import ru.valera.bot.to.MessageType;
import ru.valera.bot.to.UpdateTO;

import java.util.Set;

import static ru.valera.bot.to.MessageType.*;
import static ru.valera.bot.util.MessageUtil.getContent;
import static ru.valera.bot.util.MessageUtil.getRandomListIdx;
import static ru.valera.bot.util.WordsUtil.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class SimpleMessage implements Handler {
    private final String END_LINE = "\n";

    @Autowired
    private final MainKeyboard keyboard;

    @Override
    public Content handle(UpdateTO updateTO) {
        log.debug("get message template for message {}", updateTO.getMessage());
        MessageType messageType = updateTO.getMessageType();

        switch (messageType) {
            case GREETING, NEW_USER -> {
                return getContent(GREETINGS.get(getRandomListIdx(GREETINGS.size())), updateTO);
            }
            case VALERA -> {
                return getContent(ANSWER_FOR_VALERA.get(getRandomListIdx(ANSWER_FOR_VALERA.size())), updateTO, keyboard);
            }
            case HELP -> {
                return getContent(MESSAGE_BOT_DESCRIPTION, updateTO);
            }
            case STICKER -> {
                return getContent("StickerID: " + updateTO.getChatId(), updateTO);
            }
        }
        return null;
    }

    @Override
    public Set<MessageType> getMessageType() {
        return Set.of(GREETING, VALERA, STICKER,
                HELP, NEW_USER);
    }
}
