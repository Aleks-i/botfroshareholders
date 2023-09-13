package ru.bot.valera.bot.service.schedulers.tasks.mansgif;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.service.schedulers.tasks.AbstractJob;

import static ru.bot.valera.bot.model.Command.MANS_GIF;

@Slf4j
@Component
public class MansGifJob extends AbstractJob {

    @Override
    public Command getCommandType() {
        return MANS_GIF;
    }
}
