package valera_bot.model;

import org.springframework.stereotype.Component;

@Component
public class Currency {
    private String nameEuro;
    private String priceEuro;
    private String nameUSD;
    private String priceUSD;
    private String nameBTC;
    private String priceBTC;
    private String volume24BTC;

    public Currency() {
    }

    public String getNameEuro() {
        return nameEuro;
    }

    public void setNameEuro(String nameEuro) {
        this.nameEuro = nameEuro;
    }

    public String getPriceEuro() {
        return priceEuro;
    }

    public void setPriceEuro(String priceEuro) {
        this.priceEuro = priceEuro;
    }

    public String getNameUSD() {
        return nameUSD;
    }

    public void setNameUSD(String nameUSD) {
        this.nameUSD = nameUSD;
    }

    public String getPriceUSD() {
        return priceUSD;
    }

    public void setPriceUSD(String priceUSD) {
        this.priceUSD = priceUSD;
    }

    public String getNameBTC() {
        return nameBTC;
    }

    public void setNameBTC(String nameBTC) {
        this.nameBTC = nameBTC;
    }

    public String getPriceBTC() {
        return priceBTC;
    }

    public void setPriceBTC(String priceBTC) {
        this.priceBTC = priceBTC;
    }

    public String getVolume24BTC() {
        return volume24BTC;
    }

    public void setVolume24BTC(String volume24BTC) {
        this.volume24BTC = volume24BTC;
    }

    @Override
    public String toString() {
        String string = String.format("\n%-10s %-14s %-10s" +
                        "\n%-12s %-14s %-10s" +
                        "\n%-10s %-10s %-10s" +
                        "\n%-25s %-5s %-10s",
                "еврик:", priceEuro, "деревянных",
                "багз:", priceUSD, "деревянных",
                "биток:", priceBTC, "багсов",
                "торги по битку в сутки", volume24BTC, "багсов");
        System.out.println(string);
        return string;
    }
}
