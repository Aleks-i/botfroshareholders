package ru.valera.bot.service.mailer.timedelay;

import org.quartz.CronScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Component;
import ru.valera.bot.model.chat.MailerType;
import ru.valera.bot.service.mailer.MailerTrigger;

@Component
public class DelaysTrigger implements MailerTrigger {

    public Trigger initTrigger() {
        return TriggerBuilder
                .newTrigger()
                .withIdentity(this.getClass().getSimpleName(), this.getSenderType().name())
                .withSchedule(
                        CronScheduleBuilder.dailyAtHourAndMinute(8, 0))
                .build();
    }

    @Override
    public MailerType getSenderType() {
        return MailerType.TIMES_DELAY;
    }
}
