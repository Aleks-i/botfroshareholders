package ru.bot.valera.bot.service.schedulers;

import org.quartz.CronScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Component;

@Component
public class SchedulersStarterTrigger {

    public Trigger initTrigger() {
        return TriggerBuilder
                .newTrigger()
                .withIdentity(this.getClass().getSimpleName(), "SCHEDULERS_STARTER" )
                .withSchedule(
                        CronScheduleBuilder.dailyAtHourAndMinute(0, 0))
                .build();
    }
}
