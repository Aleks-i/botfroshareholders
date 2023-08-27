package ru.valera.bot.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

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
}
