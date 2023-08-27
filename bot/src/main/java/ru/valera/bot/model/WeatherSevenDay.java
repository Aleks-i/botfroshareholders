package ru.valera.bot.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WeatherSevenDay {
    private LocalDate date;
    private Double dailyTempMin;
    private Double dailyTempMax;
    private Double dailyTempMorn;
    private Double dailyTempDay;
    private Double dailyTempEve;
    private Double dailyTempNight;
    private Double dailyHumidity;
    private Double dailyWindSpeed;
    private Double dailyClouds;
    private Double dailyPop;                    //вероятность выпадения осадков
    private String dailyWeatherDescription;
    private String dailyWeatherIcon;

}
