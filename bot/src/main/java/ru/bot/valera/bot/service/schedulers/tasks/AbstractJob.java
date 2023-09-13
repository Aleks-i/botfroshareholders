package ru.bot.valera.bot.service.schedulers.tasks;

import org.quartz.JobExecutionContext;
import ru.bot.valera.bot.to.UpdateTO;

import static ru.bot.valera.bot.bot.Bot.CHAT_STORAGE;
import static ru.bot.valera.bot.bot.MessageReceiver.receiveQueue;

public abstract class AbstractJob implements MailerJob {

    public void execute(JobExecutionContext context) {

        CHAT_STORAGE.entrySet().stream()
                .filter(es -> es.getValue().getMailerMap().get(this.getCommandType()))
                .map(es -> new UpdateTO(this.getCommandType(), es.getKey()))
                .forEach(receiveQueue::add);
        System.gc();
    }
}
