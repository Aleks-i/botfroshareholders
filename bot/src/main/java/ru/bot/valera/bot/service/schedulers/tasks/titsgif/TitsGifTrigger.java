package ru.bot.valera.bot.service.schedulers.tasks.titsgif;

import org.quartz.Trigger;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.service.schedulers.tasks.AbstractTrigger;

import static ru.bot.valera.bot.util.SchedulerUtil.titsGifMinutes;

@Component
public class TitsGifTrigger extends AbstractTrigger {

    public Trigger initTrigger() {
        return bildTrigger(this, 10, titsGifMinutes);
    }

    @Override
    public Command getSchedulerType() {
        return Command.TITS_GIF;
    }
}
