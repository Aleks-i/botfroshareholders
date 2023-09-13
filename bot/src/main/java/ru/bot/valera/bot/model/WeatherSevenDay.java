package ru.bot.valera.bot.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WeatherSevenDay {
    LocalDate date;
    Double dailyTempMin;
    Double dailyTempMax;
    Double dailyTempMorn;
    Double dailyTempDay;
    Double dailyTempEve;
    Double dailyTempNight;
    Double dailyHumidity;
    Double dailyWindSpeed;
    Double dailyClouds;
    Double dailyPop;                    //вероятность выпадения осадков
    String dailyWeatherDescription;
    String dailyWeatherIcon;

}
