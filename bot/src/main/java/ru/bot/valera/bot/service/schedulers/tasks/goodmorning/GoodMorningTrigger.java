package ru.bot.valera.bot.service.schedulers.tasks.goodmorning;

import org.quartz.Trigger;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.service.schedulers.tasks.AbstractTrigger;

import static ru.bot.valera.bot.model.Command.GOOD_MORNING;
import static ru.bot.valera.bot.util.SchedulerUtil.goodMorningMinutes;

@Component
public class GoodMorningTrigger extends AbstractTrigger {

    public Trigger initTrigger() {
        return bildTrigger(this, 9, goodMorningMinutes);
    }

    @Override
    public Command getSchedulerType() {
        return GOOD_MORNING;
    }
}
