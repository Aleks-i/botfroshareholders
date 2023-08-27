package ru.valera.bot.model.crypta.coinmarketcap;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class FiatRub {

    @JsonProperty("data")
    public Map<Integer, Coin> data;

    public static class Coin {
        public int id;
        public String name;
        public String symbol;
        public Quote quote;

        public static class Quote {
            @JsonProperty("RUB")
            public RUB rub;

            public static class RUB {
                public double price;
                public double volume_24h;
            }
        }
    }
}
