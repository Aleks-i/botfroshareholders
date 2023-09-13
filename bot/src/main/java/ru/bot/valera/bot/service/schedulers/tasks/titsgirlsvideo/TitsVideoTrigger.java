package ru.bot.valera.bot.service.schedulers.tasks.titsgirlsvideo;

import org.quartz.Trigger;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.service.schedulers.tasks.AbstractTrigger;

import static ru.bot.valera.bot.util.SchedulerUtil.titsVideoMinutes;

@Component
public class TitsVideoTrigger extends AbstractTrigger {

    public Trigger initTrigger() {
        return bildTrigger(this, 12, titsVideoMinutes);
    }

    @Override
    public Command getSchedulerType() {
        return Command.TITS_VIDEO;
    }
}
