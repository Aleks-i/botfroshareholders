package ru.bot.valera.bot.service.schedulers.tasks.mansvideo;

import org.quartz.Trigger;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.service.schedulers.tasks.AbstractTrigger;

import static ru.bot.valera.bot.model.Command.MANS_VIDEO;
import static ru.bot.valera.bot.util.SchedulerUtil.mansVideoMinutes;

@Component
public class MansVideoTrigger extends AbstractTrigger {

    public Trigger initTrigger() {
        return bildTrigger(this, 17, mansVideoMinutes);
    }

    @Override
    public Command getSchedulerType() {
        return MANS_VIDEO;
    }
}
