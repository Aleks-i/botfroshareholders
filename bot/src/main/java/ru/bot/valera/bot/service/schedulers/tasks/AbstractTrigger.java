package ru.bot.valera.bot.service.schedulers.tasks;

import org.quartz.CronScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

public abstract class AbstractTrigger implements MailerTrigger {

    public Trigger bildTrigger(MailerTrigger trigger, int hour, int minutes) {
        return TriggerBuilder
                .newTrigger()
                .withIdentity(trigger.getClass().getSimpleName(), "Mailer group trigger" )
                .withSchedule(
                        CronScheduleBuilder.dailyAtHourAndMinute(hour, minutes))
                .build();
    }

    public Trigger bildTrigger(MailerTrigger trigger, int hour, int minutes, Integer... daysOfWeek) {
        return TriggerBuilder
                .newTrigger()
                .withIdentity(trigger.getClass().getSimpleName(), "Mailer group trigger" )
                .withSchedule(
                        CronScheduleBuilder.atHourAndMinuteOnGivenDaysOfWeek(hour, minutes, daysOfWeek))
                .build();
    }
}
