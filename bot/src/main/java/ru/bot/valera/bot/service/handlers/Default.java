package ru.bot.valera.bot.service.handlers;

import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.to.UpdateTo;

import java.util.Set;

@Component
public class Default extends AbstractContent {

    @Override
    public Content handle(UpdateTo updateTo) {
        return getContent("", updateTo);
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(Command.NONE);
    }
}
