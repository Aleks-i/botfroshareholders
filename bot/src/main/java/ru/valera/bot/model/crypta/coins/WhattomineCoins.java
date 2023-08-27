package ru.valera.bot.model.crypta.coins;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
public class WhattomineCoins {
    @JsonProperty("coins")
    Map<String, CoinProfit> coinsMap;

    @Setter
    @Getter
    public static class CoinProfit {
        public long id;
        public String tag;
        public String algorithm;
        public String block_time;
        public double block_reward;
        public double block_reward24;
        public double last_block;
        public double difficulty;
        public double difficulty24;
        public double nethash;
        public double exchange_rate;
        public double exchange_rate24;
        public double exchange_rate_vol;
        public String exchange_rate_curr;
        public String market_cap;
        public String estimated_rewards;
        public String estimated_rewards24;
        public String btc_revenue;
        public String btc_revenue24;
        public int profitability;
        public int profitability24;
        public boolean lagging;
        public long timestamp;
    }
}
