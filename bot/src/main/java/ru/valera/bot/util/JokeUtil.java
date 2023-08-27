package ru.valera.bot.util;

import java.util.concurrent.ThreadLocalRandom;

public class JokeUtil {
    private static final long QUANTITY_ANECDOTES_DB = 124_166;
    private static final long QUANTITY_STATUSES_DB = 738;

    public static long getRandomAnekdoteId() {
        return getRandomId(QUANTITY_ANECDOTES_DB);
    }

    public static long getRandomStatusId() {
        return getRandomId(QUANTITY_STATUSES_DB);
    }

    public static long getRandomId(long count) {
        return ThreadLocalRandom.current().nextLong(1, count + 1L);
    }
}
