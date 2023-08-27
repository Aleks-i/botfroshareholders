package ru.valera.bot.model.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class CurrentWeather {
    private int visibility;
    private int timezone;
    private Main main;
    private Clouds clouds;
    private Sys sys;
    private int dt;
    private Coord coord;
    private ArrayList<Weather> weather;
    private String name;
    private int cod;
    private int id;
    private String base;
    private Wind wind;

    @Override
    public String toString() {
        return name + ": " + "\n"
                + "температура: " + main.getTemp() + "C" + "\n"
                + "по ощущениям: " + main.getFeelsLike() + "C" + "\n"
                + "скорость ветра: " + wind.getSpeed() + "м/с" + "\n"
                + "влажность: " + main.getHumidity() + "%" + "\n"
                + "облачность: " + clouds.getAll() + "%" + "\n"
                + weather.get(0).getDescription() + "\n"
//               + "http://openweathermap.org/img/wn/" + weather.get(0).getIcon() + ".png"
                ;
    }


    @Getter
    public static class Main {
        @JsonProperty("temp_max")
        private double tempMax;
        private double temp;
        @JsonProperty("feels_like")
        private double feelsLike;
        private int humidity;
        private int pressure;
        @JsonProperty("temp_min")
        private double tempMin;
    }

    @Getter
    public static class Clouds {
        private int all;
    }

    @Getter
    public static class Sys {
        private String country;
        private int sunrise;
        private int sunset;
        private int id;
        private int type;
    }

    @Getter
    public static class Coord {
        private double lon;
        private double lat;
    }

    @Getter
    public static class Weather {
        private String icon;
        private String description;
        private String main;
        private int id;
    }

    @Getter
    public static class Wind {
        private int deg;
        private int speed;
        private int gust;
    }
}
