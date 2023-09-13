package ru.bot.valera.bot.service.schedulers.tasks.statuses;

import org.quartz.Trigger;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.service.schedulers.tasks.AbstractTrigger;

import static ru.bot.valera.bot.util.SchedulerUtil.statusTime;

@Component
public class StatusesTrigger extends AbstractTrigger {

    public Trigger initTrigger() {
        return bildTrigger(this, statusTime[0], statusTime[1]);
    }

    @Override
    public Command getSchedulerType() {
        return Command.STATUSES;
    }
}
