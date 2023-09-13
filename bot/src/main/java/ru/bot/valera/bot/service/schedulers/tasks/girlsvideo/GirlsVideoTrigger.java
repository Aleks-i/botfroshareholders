package ru.bot.valera.bot.service.schedulers.tasks.girlsvideo;

import org.quartz.Trigger;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.service.schedulers.tasks.AbstractTrigger;

import static ru.bot.valera.bot.model.Command.GIRLS_VIDEO;
import static ru.bot.valera.bot.util.SchedulerUtil.girlsVideoMinutes;

@Component
public class GirlsVideoTrigger extends AbstractTrigger {

    public Trigger initTrigger() {
        return bildTrigger(this, 15, girlsVideoMinutes);
    }

    @Override
    public Command getSchedulerType() {
        return GIRLS_VIDEO;
    }
}
