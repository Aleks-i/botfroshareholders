package ru.bot.valera.bot.service.schedulers.tasks.goodnight;

import org.quartz.Trigger;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.service.schedulers.tasks.AbstractTrigger;

import static ru.bot.valera.bot.model.Command.GOOD_NIGHT;
import static ru.bot.valera.bot.util.SchedulerUtil.goodNightMinutes;

@Component
public class GoodNightTrigger extends AbstractTrigger {

    public Trigger initTrigger() {
        return bildTrigger(this, 21, goodNightMinutes);
    }

    @Override
    public Command getSchedulerType() {
        return GOOD_NIGHT;
    }
}
