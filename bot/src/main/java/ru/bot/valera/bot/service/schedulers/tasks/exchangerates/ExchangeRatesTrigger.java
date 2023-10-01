package ru.bot.valera.bot.service.schedulers.tasks.exchangerates;

import org.quartz.Trigger;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.service.schedulers.tasks.AbstractTrigger;

import static ru.bot.valera.bot.util.SchedulerUtil.exchangeRatesMinutes;

@Component
public class ExchangeRatesTrigger extends AbstractTrigger {

    public Trigger initTrigger() {
        return bildTrigger(this, 13, exchangeRatesMinutes, 2, 6);
    }

    @Override
    public Command getSchedulerType() {
        return Command.CURRENCY;
    }
}
