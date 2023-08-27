package ru.valera.bot.model.crypta;

public class Profit implements Comparable<Profit> {

    public String symbol;
    public double profit;
    public double power;
    public String source;

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
