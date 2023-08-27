package ru.valera.bot.service.mailer;

import org.quartz.Trigger;
import ru.valera.bot.model.chat.MailerType;

public interface MailerTrigger {
    MailerType getSenderType();

    Trigger initTrigger();
}
