package ru.bot.valera.bot.service.schedulers.tasks.rock;

import org.quartz.Trigger;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.service.schedulers.tasks.AbstractTrigger;

import static ru.bot.valera.bot.util.SchedulerUtil.rockTime;

@Component
public class RockTrigger extends AbstractTrigger {

    public Trigger initTrigger() {
        return bildTrigger(this, rockTime[0], rockTime[1], 1, 3, 5);
    }

    @Override
    public Command getSchedulerType() {
        return Command.ROCK;
    }
}
