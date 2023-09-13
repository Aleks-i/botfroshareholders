package ru.bot.valera.bot.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Data
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Currencyes {
    String symbolEuro;
    String priceEuro;
    String symbolUSD;
    String priceUSD;
    String symbolYUAN;
    String priceYUAN;
    String symbolBTC;
    String priceBTC;
    String volume24BTC;

    public void setEuro(String symbol, String price) {
        this.symbolEuro = symbol;
        this.priceEuro = price;
    }

    public void setUSD(String symbol, String price) {
        this.symbolUSD = symbol;
        this.priceUSD = price;
    }

    public void setYUAN(String symbol, String price) {
        this.symbolYUAN = symbol;
        this.priceYUAN = price;
    }

    public void setBTC(String symbol, String price, String volume24BTC) {
        this.symbolBTC = symbol;
        this.priceBTC = price;
        this.volume24BTC = volume24BTC;
    }

    @Override
    public String toString() {
        return String.format("""
                        %-10s %-14s %-10s
                        %-12s %-15s %-10s
                        %-10s %-15s %-10s
                        %-10s %-11s %-10s
                        %s
                        %-25s %-5s %-10s""",
                "еврик:", priceEuro, "рубликов",
                "бакс:", priceUSD, "рублищ",
                "юань:", priceYUAN, "рублишек",
                "биток:", priceBTC, "$",
                "",
                "торги по битку в сутки", volume24BTC, "$" );
    }
}
