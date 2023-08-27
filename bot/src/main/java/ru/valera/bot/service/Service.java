package ru.valera.bot.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.valera.bot.model.Content;
import ru.valera.bot.model.chat.MailerType;
import ru.valera.bot.service.handler.Handler;
import ru.valera.bot.service.mailer.MailerJob;
import ru.valera.bot.service.mailer.MailerTrigger;
import ru.valera.bot.to.MessageType;
import ru.valera.bot.to.UpdateTO;

import java.util.Map;

import static ru.valera.bot.bot.MessageSender.sendQueue;

@Slf4j
@Component
@RequiredArgsConstructor
public class Service {

    private final Map<MessageType, Handler> handlerMap;
    private final Map<MailerType, MailerJob> jobMap;
    private final Map<MailerType, MailerTrigger> triggerMap;
    private final ParserCommand parserCommand;

    public void operate(Update update) {
        UpdateTO updateTO = parserCommand.getParsedUpdate(update);
        MessageType messageType = updateTO.getMessageType();
        if (messageType != MessageType.DEFAULT) {
            Handler handler = handlerMap.get(messageType);
            Content content = handler.handle(updateTO);
            sendQueue.add(content);
        }
    }

    @PostConstruct
    public void initSchedulers() {
        jobMap.forEach((key, value) -> {
            JobDetail job = JobBuilder.newJob(value.getClass())
                    .withIdentity(triggerMap.get(key).getClass().getSimpleName(), key.name())
                    .build();
            try {
                Scheduler scheduler = new StdSchedulerFactory().getScheduler();
                scheduler.start();
                scheduler.scheduleJob(job, triggerMap.get(key).initTrigger());
            } catch (SchedulerException e) {
                log.error(e.toString());
            }

        });
    }
}
