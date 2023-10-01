package ru.bot.valera.bot.service.schedulers.tasks.events.of.day;

import org.quartz.Trigger;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.service.schedulers.tasks.AbstractTrigger;

import static ru.bot.valera.bot.util.SchedulerUtil.eventsOfDay;

@Component
public class EventsOfDayTrigger extends AbstractTrigger {

    public Trigger initTrigger() {
        return bildTrigger(this, 0, eventsOfDay);
    }

    @Override
    public Command getSchedulerType() {
        return Command.MAIN_EVENTS_OF_DAY;
    }
}
