package ru.bot.valera.bot.service.handler.exception;

import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.service.handler.AbstractContent;
import ru.bot.valera.bot.to.UpdateTO;

import java.util.Set;

@Component
public class ExceptionHandler extends AbstractContent {

    @Override
    public Content handle(UpdateTO updateTO) {
        return getContent(updateTO.getErrorMessage(), updateTO);
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(Command.EXCEPTION_HANDLER);
    }
}
