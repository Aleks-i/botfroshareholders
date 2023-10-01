package ru.bot.valera.bot.service.schedulers;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import static ru.bot.valera.bot.bot.Bot.CHATS_CONTENT_COUNTER;
import static ru.bot.valera.bot.util.SchedulerUtil.*;

@Slf4j
@Component
public class SchedulersStarterJob implements Job {

    public void execute(JobExecutionContext context) {

        goodMorningMinutes = getRandomMinutesForFirstHalfHour();
        goodNightMinutes = getRandomMinutesForHour();
        fridayMinutes = getRandomMinutesForHour();

        horoscopeMinutes = getRandomMinutesForHour();

        titsGifMinutes = getRandomMinutesForHour();
        titsVideoMinutes = getRandomMinutesForHour();
        girlsVideoMinutes = getRandomMinutesForHour();

        mansVideoMinutes = getRandomMinutesForHour();
        mansGifMinutes = getRandomMinutesForHour();

        exchangeRatesMinutes = getRandomMinutesForHour();

        anecdoteTime = getRandomTime(10, 22);
        statusTime = getRandomTime(10, 22);

        CHATS_CONTENT_COUNTER.forEach((id, m) -> m.forEach((c, chc) -> {
            chc.setCount(0);
            chc.setShowNotify(false);
        }));

        SchedulersStarter schedulersStarter = getSchedulerProperty(context);
        schedulersStarter.updateTriggers();
    }
}
