package ru.bot.valera.bot.service.schedulers.tasks.friday;

import org.quartz.Trigger;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.service.schedulers.tasks.AbstractTrigger;

import static ru.bot.valera.bot.model.Command.FRIDAY;
import static ru.bot.valera.bot.util.SchedulerUtil.fridayMinutes;

@Component
public class FridayTrigger extends AbstractTrigger {

    public Trigger initTrigger() {
        return bildTrigger(this, 17, fridayMinutes, 6);
    }

    @Override
    public Command getSchedulerType() {
        return FRIDAY;
    }
}
