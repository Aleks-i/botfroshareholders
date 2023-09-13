package ru.bot.valera.bot.service.schedulers.tasks.titsgirlsvideo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.service.schedulers.tasks.AbstractJob;

@Slf4j
@Component
public class TitsVideoJob extends AbstractJob {

    @Override
    public Command getCommandType() {
        return Command.TITS_VIDEO;
    }
}
