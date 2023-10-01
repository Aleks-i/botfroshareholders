package ru.bot.valera.bot.service.schedulers.tasks.weather.ekb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.service.schedulers.tasks.AbstractJob;

import static ru.bot.valera.bot.model.Command.*;

@Slf4j
@Component
public class WeatherEkbJob extends AbstractJob {

    @Override
    public Command getCommandType() {
        return EDIT_WEATHER_KEYBOARD;
    }
}
