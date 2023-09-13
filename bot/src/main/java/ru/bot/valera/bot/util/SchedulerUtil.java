package ru.bot.valera.bot.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import ru.bot.valera.bot.service.schedulers.SchedulersStarter;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@UtilityClass
public class SchedulerUtil {

    public static String SCHEDULERS_STARTER_NAME_PROPERTY = "schedulersStarter";
    public static String MAILER_NAME_PROPERTY = "mailer";

    public static int goodMorningMinutes = getRandomMinutesForHour();
    public static int goodNightMinutes = getRandomMinutesForHour();
    public static int fridayMinutes = getRandomMinutesForHour();

    public static int horoscopeMinutes = getRandomMinutesForHour();

    public static int titsGifMinutes = getRandomMinutesForHour();
    public static int titsVideoMinutes = getRandomMinutesForHour();
    public static int girlsVideoMinutes = getRandomMinutesForHour();

    public static int mansVideoMinutes = getRandomMinutesForHour();
    public static int mansGifMinutes = getRandomMinutesForHour();

    public static int exchangeRates = getRandomMinutesForHour();

    public static int[] anecdoteTime = getRandomTime(10, 22);
    public static int[] statusTime = getRandomTime(10, 22);

    public static int[] getRandomTime(int start, int end) {
        int[] result = new int[2];
        result[0] = getRandom(start, end);
        result[1] = getRandomMinutesForHour();
        return result;
    }

    public static int getRandomMinutesForHour() {
        return getRandom(0, 59);
    }

    private static int getRandom(int start, int end) {
        return ThreadLocalRandom.current().nextInt(start, end);
    }

    public static SchedulersStarter getSchedulerProperty(JobExecutionContext context) {
        return (SchedulersStarter) getProperty(context, SCHEDULERS_STARTER_NAME_PROPERTY);
    }

    private static Object getProperty(JobExecutionContext context, String propertyName) {
        SchedulerContext schedulerContext = null;
        try {
            schedulerContext = context.getScheduler().getContext();
        } catch (SchedulerException e) {
            log.warn(e.getMessage());
        }
        assert schedulerContext != null;
        return schedulerContext.get(propertyName);
    }
}
