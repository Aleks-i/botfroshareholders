package ru.bot.valera.bot.service.schedulers.tasks.goodmorning;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.service.schedulers.tasks.AbstractJob;

import static ru.bot.valera.bot.model.Command.GOOD_MORNING;

@Slf4j
@Component
public class GoodMorningJob extends AbstractJob {

    @Override
    public Command getCommandType() {
        return GOOD_MORNING;
    }
}
