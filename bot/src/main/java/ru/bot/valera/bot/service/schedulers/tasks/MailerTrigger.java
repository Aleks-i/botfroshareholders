package ru.bot.valera.bot.service.schedulers.tasks;

import org.quartz.Trigger;
import ru.bot.valera.bot.model.Command;

public interface MailerTrigger {
    Command getSchedulerType();

    Trigger initTrigger();
}
