package ru.bot.valera.bot.service.schedulers.tasks;

import lombok.RequiredArgsConstructor;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.to.UpdateTo;

import static ru.bot.valera.bot.bot.MessageReceiver.receiveQueue;
import static ru.bot.valera.bot.model.SourceMessageType.TASK;

@Component
@RequiredArgsConstructor
public abstract class AbstractJob implements MailerJob {

    public void execute(JobExecutionContext context) {

        receiveQueue.add(new UpdateTo(TASK, this.getCommandType()));
    }
}
