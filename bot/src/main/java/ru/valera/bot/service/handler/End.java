package ru.valera.bot.service.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.valera.bot.model.Content;
import ru.valera.bot.to.MessageType;
import ru.valera.bot.to.UpdateTO;

import java.util.Set;

import static ru.valera.bot.util.MessageUtil.getContent;
import static ru.valera.bot.util.WordsUtil.MESSAGE_BOT_DELETE;

@Component
@RequiredArgsConstructor
public class End implements Handler {

    @Override
    public Content handle(UpdateTO updateTO) {
        long chatId = updateTO.getChatId();
        //TODO не отправлять сюда сообщения из рассылки
        return getContent(MESSAGE_BOT_DELETE, updateTO);
    }

    @Override
    public Set<MessageType> getMessageType() {
        return Set.of(MessageType.END);
    }
}
