package ru.bot.valera.bot.service.handlers;

import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.to.UpdateTo;

import java.util.Set;

public interface Handler {

    Content handle(UpdateTo updateTo);

    Set<Command> getMessageType();
}
