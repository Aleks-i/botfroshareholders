package ru.valera.bot.service.mailer.titsgirlsvideo;

import org.quartz.CronScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Component;
import ru.valera.bot.model.chat.MailerType;
import ru.valera.bot.service.mailer.MailerTrigger;

import static ru.valera.bot.util.SchedulerUtil.titsVideoDelay;

@Component
public class TitsVideoTrigger implements MailerTrigger {

    public Trigger initTrigger() {
        return TriggerBuilder
                .newTrigger()
                .withIdentity(this.getClass().getSimpleName(), this.getSenderType().name())
                .withSchedule(
                        CronScheduleBuilder.dailyAtHourAndMinute(11, titsVideoDelay))
                .build();
    }

    @Override
    public MailerType getSenderType() {
        return MailerType.TITS_VIDEO;
    }
}
