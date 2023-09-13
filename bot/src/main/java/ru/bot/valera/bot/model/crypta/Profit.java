package ru.bot.valera.bot.model.crypta;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Profit implements Comparable<Profit> {

    String symbol;
    double profit;
    double power;
    String source;

    @Override
    public int compareTo(Profit p) {
        return Double.compare(this.profit, p.profit);
    }

    @Override
    public String
    toString() {
        return "Profit{" +
                "symbol='" + symbol + '\'' +
                ", profit=" + profit +
                ", power=" + power +
                ", source='" + source + '\'' +
                '}';
    }
}
