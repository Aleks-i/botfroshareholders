package ru.valera.bot.model.crypta.coins;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DataProfit {
//    Device device;
//    private Map<String, DataProfit> dataMap;

//    public static class Device {
//        String name;
//        String brand;
//    }

    private Profit profit;
    private Profit24 profit24;

    @Getter
    @Setter
    public static class Profit {
        private String coin;
        private String logo;
        private double revenueUSD;
        private double profitUSD;
        @JsonIgnore
        private String power;
    }

    @Getter
    @Setter
    public static class Profit24 {
        private String coin;
        private String logo;
        private double revenueUSD24;
        private double profitUSD24;
        private String power;
    }
}
