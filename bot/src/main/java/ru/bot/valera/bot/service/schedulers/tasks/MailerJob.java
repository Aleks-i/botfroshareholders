package ru.bot.valera.bot.service.schedulers.tasks;

import org.quartz.Job;
import ru.bot.valera.bot.model.Command;

public interface MailerJob extends Job {

    Command getCommandType();
}
