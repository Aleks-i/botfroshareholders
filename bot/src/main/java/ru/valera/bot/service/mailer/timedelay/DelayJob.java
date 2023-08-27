package ru.valera.bot.service.mailer.timedelay;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;
import ru.valera.bot.model.chat.MailerType;
import ru.valera.bot.service.mailer.MailerJob;

import static ru.valera.bot.util.SchedulerUtil.*;

@Slf4j
@Component
public class DelayJob implements MailerJob {

    public void execute(JobExecutionContext context) {
        holidaysDelay = getRandomMinutes();
        titsGifDelay = getRandomMinutes();
        titsVideoDelay = getRandomMinutes();
    }

    @Override
    public MailerType getSenderType() {
        return MailerType.TIMES_DELAY;
    }
}
