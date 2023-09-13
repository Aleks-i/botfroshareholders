package ru.bot.valera.bot.service.schedulers.tasks.anecdote;

import org.quartz.Trigger;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.service.schedulers.tasks.AbstractTrigger;

import static ru.bot.valera.bot.util.SchedulerUtil.anecdoteTime;

@Component
public class AnecdoteTrigger extends AbstractTrigger {

    public Trigger initTrigger() {
        return bildTrigger(this, anecdoteTime[0], anecdoteTime[1]);
    }

    @Override
    public Command getSchedulerType() {
        return Command.ANECDOTE;
    }
}
