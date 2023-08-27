package ru.valera.bot.service.handler;

import org.springframework.stereotype.Component;
import ru.valera.bot.model.Content;
import ru.valera.bot.to.MessageType;
import ru.valera.bot.to.UpdateTO;
import ru.valera.bot.util.MessageUtil;

import java.util.Set;

@Component
public class Default implements Handler {

    @Override
    public Content handle(UpdateTO updateTO) {
        return MessageUtil.getContent("", updateTO);
    }

    @Override
    public Set<MessageType> getMessageType() {
        return Set.of(MessageType.DEFAULT);
    }
}
