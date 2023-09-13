package ru.bot.valera.bot.model.crypta.coins;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DataProfit {
//    Device device;
//    private Map<String, DataProfit> dataMap;

//    public static class Device {
//        String name;
//        String brand;
//    }

    Profit profit;
    Profit24 profit24;

    @Getter
    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Profit {
        String coin;
        String logo;
        double revenueUSD;
        double profitUSD;
        @JsonIgnore
        String power;
    }

    @Getter
    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Profit24 {
        String coin;
        String logo;
        double revenueUSD24;
        double profitUSD24;
        String power;
    }
}
