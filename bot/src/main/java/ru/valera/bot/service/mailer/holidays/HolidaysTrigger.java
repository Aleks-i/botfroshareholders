package ru.valera.bot.service.mailer.holidays;

import org.quartz.CronScheduleBuilder;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Component;
import ru.valera.bot.model.chat.MailerType;
import ru.valera.bot.service.mailer.MailerTrigger;

@Component
public class HolidaysTrigger implements MailerTrigger {

    public org.quartz.Trigger initTrigger() {
        return TriggerBuilder
                .newTrigger()
                .withIdentity(this.getClass().getSimpleName(), this.getSenderType().name())
                .withSchedule(
                        CronScheduleBuilder.cronSchedule("* * * * * ?"))
                .build();
    }

    @Override
    public MailerType getSenderType() {
        return MailerType.HOLIDAYS;
    }
}
