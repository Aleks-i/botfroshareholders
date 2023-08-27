package ru.valera.bot.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Currencyes {
    private String symbolEuro;
    private String priceEuro;
    private String symbolUSD;
    private String priceUSD;
    private String symbolYUAN;
    private String priceYUAN;
    private String symbolBTC;
    private String priceBTC;
    private String volume24BTC;

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
                "торги по битку в сутки", volume24BTC, "$");
    }
}
