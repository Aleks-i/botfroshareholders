package ru.bot.valera.bot.util;

import lombok.experimental.UtilityClass;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.service.handler.exception.ContentShowCounter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static ru.bot.valera.bot.bot.Bot.CHATS_CONTENT_COUNTER;
import static ru.bot.valera.bot.model.Command.*;

@UtilityClass
public class Util {

    public static final int ID_BTC_COINMARKETCAP = 1;
    public static final int ID_USD_COINMARKETCAP = 2781;
    public static final int ID_YUAN_COINMARKETCAP = 2787;
    public static final int ID_EUR_COINMARKETCAP = 2790;

    public static String formatterBigDecimal(double price) {
        String string = String.valueOf(price);
        DecimalFormat df = new DecimalFormat(
                "#,##0.00",
                new DecimalFormatSymbols(new Locale("pt", "BR")));
        BigDecimal value = new BigDecimal(string);
        return df.format(value.floatValue());
    }

    public static void initContentCounter(long chatId) {
        Map<Command, ContentShowCounter> contentCounter = new ConcurrentHashMap<>(
                Map.of(ANECDOTE, new ContentShowCounter(),
                        STATUSES, new ContentShowCounter(),
                        TITS_GIF, new ContentShowCounter(),
                        TITS_VIDEO, new ContentShowCounter(),
                        GIRLS_VIDEO, new ContentShowCounter(),
                        MINING, new ContentShowCounter(),
                        CALLBACK_MINING, new ContentShowCounter())
        );
        CHATS_CONTENT_COUNTER.put(chatId, contentCounter);
    }
}
