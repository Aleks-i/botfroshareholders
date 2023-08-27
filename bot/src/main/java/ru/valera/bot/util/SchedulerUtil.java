package ru.valera.bot.util;

import java.util.concurrent.ThreadLocalRandom;

public class SchedulerUtil {

    public static int holidaysDelay = 0;
    public static int titsGifDelay = 0;
    public static int titsVideoDelay = 0;

    public static int getRandomMinutes() {
        return ThreadLocalRandom.current().nextInt(0, 59);
    }
}
