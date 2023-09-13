package ru.bot.valera.bot.service.schedulers.tasks.mansgif;

import org.quartz.Trigger;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.service.schedulers.tasks.AbstractTrigger;

import static ru.bot.valera.bot.model.Command.MANS_GIF;
import static ru.bot.valera.bot.util.SchedulerUtil.mansGifMinutes;

@Component
public class MansGifTrigger extends AbstractTrigger {

    public Trigger initTrigger() {
        return bildTrigger(this, 14, mansGifMinutes);
    }

    @Override
    public Command getSchedulerType() {
        return MANS_GIF;
    }
}
