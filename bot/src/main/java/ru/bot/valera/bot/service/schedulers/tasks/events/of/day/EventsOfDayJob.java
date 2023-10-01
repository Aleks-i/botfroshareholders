package ru.bot.valera.bot.service.schedulers.tasks.events.of.day;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.service.schedulers.tasks.AbstractJob;

import static ru.bot.valera.bot.model.Command.MAIN_EVENTS_OF_DAY;

@Slf4j
@Component
public class EventsOfDayJob extends AbstractJob {

    @Override
    public Command getCommandType() {
        return MAIN_EVENTS_OF_DAY;
    }
}
