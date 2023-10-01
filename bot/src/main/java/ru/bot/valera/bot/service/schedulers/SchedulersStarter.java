package ru.bot.valera.bot.service.schedulers;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.service.Service;
import ru.bot.valera.bot.service.schedulers.tasks.MailerJob;
import ru.bot.valera.bot.service.schedulers.tasks.MailerTrigger;

import java.util.HashMap;
import java.util.Map;

import static ru.bot.valera.bot.util.SchedulerUtil.MAILER_NAME_PROPERTY;
import static ru.bot.valera.bot.util.SchedulerUtil.SCHEDULERS_STARTER_NAME_PROPERTY;

@Slf4j
@Component
@RequiredArgsConstructor
public class SchedulersStarter {

    private final Map<Command, MailerJob> jobMap;
    private final Map<Command, MailerTrigger> mailerTriggerMap;
    private final Map<Command, Trigger> triggerMap = new HashMap<>();

    private final SchedulersStarterTrigger schedulersStarterTrigger;

    private final Service service;

    @PostConstruct
    public void startSchedulers() {
        startSchedulersHandlers();
        startSchedulerStart();
    }

    public void startSchedulersHandlers() {
        jobMap.forEach((key, value) -> {
            JobDetail job = JobBuilder.newJob(value.getClass())
                    .withIdentity(value.getClass().getSimpleName(), "Mailer group job" )
                    .build();
            try {
                Scheduler scheduler = new StdSchedulerFactory().getScheduler();
                scheduler.getContext().put(MAILER_NAME_PROPERTY, service);
                scheduler.start();

                Trigger trigger = mailerTriggerMap.get(key).initTrigger();
                triggerMap.put(key, trigger);

                scheduler.scheduleJob(job, trigger);
            } catch (SchedulerException e) {
                log.error(e.toString());
            }
        });
    }

    public void updateTriggers() {
        triggerMap.forEach((key, value) -> {
            Trigger newTrigger = mailerTriggerMap.get(key).initTrigger();
            triggerMap.put(key, newTrigger);

            try {
                Scheduler scheduler = new StdSchedulerFactory().getScheduler();
                scheduler.rescheduleJob(value.getKey(), newTrigger);
            } catch (SchedulerException e) {
                log.error(e.toString());
            }
        });
    }

    private void startSchedulerStart() {
        JobDetail job = JobBuilder.newJob(SchedulersStarterJob.class)
                .withIdentity(SchedulersStarterTrigger.class.getSimpleName(), "SCHEDULERS_RSTARTE" )
                .build();
        try {
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.getContext().put(SCHEDULERS_STARTER_NAME_PROPERTY, this);
            scheduler.start();
            scheduler.scheduleJob(job, schedulersStarterTrigger.initTrigger());
        } catch (SchedulerException e) {
            log.error(e.toString());
        }
    }
}
