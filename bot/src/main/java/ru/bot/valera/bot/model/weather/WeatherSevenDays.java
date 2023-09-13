package ru.bot.valera.bot.model.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.bot.valera.bot.util.WeatherUtil.CITIES_NAMES;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WeatherSevenDays {
    String lat;
    String lon;

    public List<Daily> daily;

    @Data
    static class Daily {

        @JsonProperty("wind speed" )
        double windSpeed;

        @JsonProperty("moon phase" )
        double moonPhase;

        @JsonProperty("wind deg" )
        int windDeg;

        @JsonProperty("dew point" )
        double dewPoint;

        @JsonProperty("wind gust" )
        double windGust;

        int sunrise;
        int moonset;
        double uvi;
        int moonrise;
        int pressure;
        int clouds;
        long dt;
        double pop;
        int sunset;


        int humidity;
        double rain;

        @JsonProperty("feels like" )
        FeelsLike feelsLike;
        List<Weather> weather;
        Temp temp;

        @Data
        static class FeelsLike {
            double eve;
            double night;
            double day;
            double morn;
        }

        @Data
        static class Weather {
            String icon;
            String description;
            String main;
            int id;
        }

        @Data
        static class Temp {
            double min;
            double max;
            double eve;
            double night;
            double day;
            double morn;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        LocalDate localDate = LocalDate.now();
        sb.append(CITIES_NAMES.get(lat + " " + lon)).append(": \n" );

        for (Daily d : daily) {
            sb.append(localDate).append("\n" );
            localDate = localDate.plus(1, ChronoUnit.DAYS);
            sb.append("t min/max    " ).append(d.temp.min).append(" / " ).append(d.temp.max).append(" C\n" );
            sb.append("влажность/ветер    " ).append(d.humidity).append(" %" ).append(" / " ).append(d.windSpeed).append(" м/с\n" );
            sb.append(d.weather.get(0).description).append("\n" );
            sb.append("\n" );
        }
        return sb.toString();
    }
}
