package ru.bot.valera.bot.service.schedulers.tasks.weather.ekb;

import org.quartz.Trigger;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.service.schedulers.tasks.AbstractTrigger;

import static ru.bot.valera.bot.model.Command.EDIT_WEATHER_KEYBOARD;
import static ru.bot.valera.bot.util.SchedulerUtil.weatherEkb;

@Component
public class WeatherEkbTrigger extends AbstractTrigger {

    public Trigger initTrigger() {
        return bildTrigger(this, 9, weatherEkb, 2);
    }

    @Override
    public Command getSchedulerType() {
        return EDIT_WEATHER_KEYBOARD;
    }
}
