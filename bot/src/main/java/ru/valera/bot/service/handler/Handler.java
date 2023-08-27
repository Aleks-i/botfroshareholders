package ru.valera.bot.service.handler;

import ru.valera.bot.model.Content;
import ru.valera.bot.to.MessageType;
import ru.valera.bot.to.UpdateTO;

import java.util.Set;

public interface Handler {

    Content handle(UpdateTO updateTO);

    Set<MessageType> getMessageType();
}
