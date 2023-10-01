package ru.bot.valera.bot.util;

import lombok.experimental.UtilityClass;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.service.handlers.exception.ContentShowCounter;

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
                new DecimalFormatSymbols(new Locale("pt", "BR" )));
        BigDecimal value = new BigDecimal(string);
        return df.format(value.floatValue());
    }

    public static void initContentCounter(long chatId) {
        Map<Command, ContentShowCounter> contentCounter = new ConcurrentHashMap<>(
                Map.ofEntries(
                        Map.entry(ANECDOTE, new ContentShowCounter()),
                        Map.entry(STATUSES, new ContentShowCounter()),

                        Map.entry(TITS_GIF, new ContentShowCounter()),
                        Map.entry(TITS_VIDEO, new ContentShowCounter()),
                        Map.entry(GIRLS_VIDEO, new ContentShowCounter()),

                        Map.entry(MANS_GIF, new ContentShowCounter()),
                        Map.entry(MANS_VIDEO, new ContentShowCounter()),

                        Map.entry(GOOD_MORNING, new ContentShowCounter()),
                        Map.entry(GOOD_NIGHT, new ContentShowCounter()),
                        Map.entry(FRIDAY, new ContentShowCounter()),

                        Map.entry(ROCK, new ContentShowCounter()),

                        Map.entry(MINING, new ContentShowCounter()),
                        Map.entry(CALLBACK_MINING, new ContentShowCounter())
                ));
        CHATS_CONTENT_COUNTER.put(chatId, contentCounter);
    }
}
