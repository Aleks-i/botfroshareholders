package ru.bot.valera.bot.service.handler;

import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.to.UpdateTO;

import java.util.Set;

public interface Handler {

    Content handle(UpdateTO updateTO);

    Set<Command> getMessageType();
}
