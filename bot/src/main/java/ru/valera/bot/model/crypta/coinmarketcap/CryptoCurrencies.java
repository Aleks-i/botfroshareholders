package ru.valera.bot.model.crypta.coinmarketcap;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Setter
public class CryptoCurrencies {

    public Status status;
    public CopyOnWriteArrayList<Coin> data;

    @Override
    public String toString() {
        return "CoinMarketCapModel{" +
//                "status=" + status +
                ", data=" + data +
                '}';
    }

    @Getter
    public static class Coin {
        public int id;
        public String name;
        public String symbol;
        public String slug;
        public int num_market_pairs;
        public Date date_added;
        public List<String> tags;
        public long max_supply;
        public double circulating_supply;
        public double total_supply;
        public boolean infinite_supply;
        public Platform platform;
        public int cmc_rank;
        public Object self_reported_circulating_supply;
        public Object self_reported_market_cap;
        public Object tvl_ratio;
        public Date last_updated;
        public Quote quote;

        @Override
        public String toString() {
            return "Coin{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", symbol='" + symbol + '\'' +
                    ", quote=" + quote +
                    '}';
        }

        public static class Quote {
            @JsonProperty("USD")
            public USD uSD;

            @Override
            public String toString() {
                return "Quote{" +
                        "uSD=" + uSD +
                        '}';
            }

            public static class USD {
                public double price;
                public double volume_24h;
                public double volume_change_24h;
                public double percent_change_1h;
                public double percent_change_24h;
                public double percent_change_7d;
                public double percent_change_30d;
                public double percent_change_60d;
                public double percent_change_90d;
                public double market_cap;
                public double market_cap_dominance;
                public double fully_diluted_market_cap;
                public Object tvl;
                public Date last_updated;

                @Override
                public String toString() {
                    return "USD{" +
                            "price=" + price +
                            ", volume_24h=" + volume_24h +
                            '}';
                }
            }
        }

        public static class Platform {
            public int id;
            public String name;
            public String symbol;
            public String slug;
            public String token_address;
        }
    }

    public static class Status {
        public Date timestamp;
        public int error_code;
        public Object error_message;
        public int elapsed;
        public int credit_count;
        public Object notice;
        public int total_count;
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
