package ru.bot.valera.bot.model.crypta.coins;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NoHashrateCoin {

    String coin;
    String name;
    String algo;
    String usdPrice;
    String usdPrice1h;
    String usdPrice24h;
    String usdPrice7d;
    String coinEstimate;
    String coinEstimateUnit;
    String coinEstimate1h;
    String coinEstimate24h;
    String coinEstimate7d;
    String usdEstimate;
    String usdEstimate1h;
    String usdEstimate24h;
    String usdEstimate7d;
    String networkHashrate;
    String networkDifficulty;
    String emission;
    String emissionUSD;
}
