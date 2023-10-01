package ru.bot.valera.bot.service.schedulers.tasks.horoscope;

import org.quartz.Trigger;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.service.schedulers.tasks.AbstractTrigger;

import static ru.bot.valera.bot.util.SchedulerUtil.horoscopeMinutes;

@Component
public class HoroscopeTrigger extends AbstractTrigger {

    public Trigger initTrigger() {
        return bildTrigger(this, 10, horoscopeMinutes);
    }

    @Override
    public Command getSchedulerType() {
        return Command.HOROSCOPE;
    }
}
