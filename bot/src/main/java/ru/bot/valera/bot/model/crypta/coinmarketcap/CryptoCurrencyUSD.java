package ru.bot.valera.bot.model.crypta.coinmarketcap;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CryptoCurrencyUSD {

    @JsonProperty("data" )
    Map<Integer, Coin> data;

    @Getter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Coin {
        int id;
        String name;
        String symbol;
        Quote quote;

        @Getter
        @FieldDefaults(level = AccessLevel.PRIVATE)
        public static class Quote {
            @JsonProperty("USD" )
            USD usd;

            @Getter
            @FieldDefaults(level = AccessLevel.PRIVATE)
            public static class USD {
                double price;
                double volume_24h;
            }
        }
    }
}
