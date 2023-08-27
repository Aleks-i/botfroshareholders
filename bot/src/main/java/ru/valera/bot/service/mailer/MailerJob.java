package ru.valera.bot.service.mailer;

import org.quartz.Job;
import ru.valera.bot.model.chat.MailerType;

public interface MailerJob extends Job {
    MailerType getSenderType();
}
