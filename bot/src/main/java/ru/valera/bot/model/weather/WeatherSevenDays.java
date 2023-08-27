package ru.valera.bot.model.weather;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class WeatherSevenDays {
    public double lat;
    public double lon;
    public String timezone;

    @JsonProperty("timezone offset")
    public int timezoneOffset;

    public List<Daily> daily;

    static class Daily {

        @JsonProperty("wind speed")
        public double windSpeed;

        @JsonProperty("moon phase")
        public double moonPhase;

        @JsonProperty("wind deg")
        public int windDeg;

        @JsonProperty("dew point")
        public double dewPoint;

        @JsonProperty("wind gust")
        public double windGust;

        public int sunrise;
        public int moonset;
        public double uvi;
        public int moonrise;
        public int pressure;
        public int clouds;
        public long dt;
        public double pop;
        public int sunset;


        public int humidity;
        public double rain;

        @JsonProperty("feels like")
        public FeelsLike feelsLike;
        public List<Weather> weather;
        public Temp temp;

        static class FeelsLike {
            public double eve;
            public double night;
            public double day;
            public double morn;
        }

        static class Weather {
            public String icon;
            public String description;
            public String main;
            public int id;
        }

        static class Temp {
            public double min;
            public double max;
            public double eve;
            public double night;
            public double day;
            public double morn;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        LocalDate localDate = LocalDate.now();
        sb.append(timezone.split("/")[1]).append(": \n");

        for (Daily d : daily) {
            sb.append(localDate).append("\n");
            localDate = localDate.plus(1, ChronoUnit.DAYS);
            sb.append("t min/max    ").append(d.temp.min).append(" / ").append(d.temp.max).append(" C\n");
            sb.append("влажность/ветер    ").append(d.humidity).append(" %").append(" / ").append(d.windSpeed).append(" м/с\n");
            sb.append(d.weather.get(0).description).append("\n");
            sb.append("\n");
        }
        return sb.toString();
    }
}


