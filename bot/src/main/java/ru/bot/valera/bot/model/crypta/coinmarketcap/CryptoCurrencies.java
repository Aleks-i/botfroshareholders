package ru.bot.valera.bot.model.crypta.coinmarketcap;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CryptoCurrencies {

    Status status;
    CopyOnWriteArrayList<Coin> data;

    @Override
    public String toString() {
        return "CoinMarketCapModel{" +
//                "status=" + status +
                ", data=" + data +
                '}';
    }

    @Getter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Coin {
        int id;
        String name;
        String symbol;
        String slug;
        int num_market_pairs;
        Date date_added;
        List<String> tags;
        long max_supply;
        double circulating_supply;
        double total_supply;
        boolean infinite_supply;
        Platform platform;
        int cmc_rank;
        Object self_reported_circulating_supply;
        Object self_reported_market_cap;
        Object tvl_ratio;
        Date last_updated;
        Quote quote;

        @Override
        public String toString() {
            return "Coin{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", symbol='" + symbol + '\'' +
                    ", quote=" + quote +
                    '}';
        }

        @FieldDefaults(level = AccessLevel.PRIVATE)
        public static class Quote {
            @JsonProperty("USD" )
            USD uSD;

            @Override
            public String toString() {
                return "Quote{" +
                        "uSD=" + uSD +
                        '}';
            }

            @FieldDefaults(level = AccessLevel.PRIVATE)
            public static class USD {
                double price;
                double volume_24h;
                double volume_change_24h;
                double percent_change_1h;
                double percent_change_24h;
                double percent_change_7d;
                double percent_change_30d;
                double percent_change_60d;
                double percent_change_90d;
                double market_cap;
                double market_cap_dominance;
                double fully_diluted_market_cap;
                Object tvl;
                Date last_updated;

                @Override
                public String toString() {
                    return "USD{" +
                            "price=" + price +
                            ", volume_24h=" + volume_24h +
                            '}';
                }
            }
        }

        @FieldDefaults(level = AccessLevel.PRIVATE)
        public static class Platform {
            int id;
            String name;
            String symbol;
            String slug;
            String token_address;
        }
    }

    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Status {
        Date timestamp;
        int error_code;
        Object error_message;
        int elapsed;
        int credit_count;
        Object notice;
        int total_count;
    }

    public Coin getCoinBySymbol(String symbol) {
        return data.stream()
                .filter(c -> c.symbol.equals(symbol))
                .findFirst()
                .orElse(null);
    }

    public Coin getCoinByID(int id) {
        return data.stream()
                .filter(c -> c.id == id)
                .findFirst()
                .orElse(null);
    }
}
