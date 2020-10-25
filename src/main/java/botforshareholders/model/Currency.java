package botforshareholders.model;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

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
        return nameEuro + " = " + priceEuro + " деревянных" + "\n" +
                nameUSD + " = " + priceUSD + " деревянных" + "\n" +
                nameBTC + " = " + priceBTC + " USD" + "\n" +
                "объем торгов битка в сутки" + " = " + volume24BTC + " USD";
    }
}
