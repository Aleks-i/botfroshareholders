package ru.bot.valera.bot.model.crypta.coins;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WhattomineCoins {
    @JsonProperty("coins" )
    Map<String, CoinProfit> coinsMap;

    @Setter
    @Getter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class CoinProfit {
        long id;
        String tag;
        String algorithm;
        String block_time;
        double block_reward;
        double block_reward24;
        double last_block;
        double difficulty;
        double difficulty24;
        double nethash;
        double exchange_rate;
        double exchange_rate24;
        double exchange_rate_vol;
        String exchange_rate_curr;
        String market_cap;
        String estimated_rewards;
        String estimated_rewards24;
        String btc_revenue;
        String btc_revenue24;
        int profitability;
        int profitability24;
        boolean lagging;
        long timestamp;
    }
}
