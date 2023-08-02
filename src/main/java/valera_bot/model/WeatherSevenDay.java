package valera_bot.model;

import java.time.LocalDate;

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

    public WeatherSevenDay(LocalDate date) {
        this.date = date;
    }


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getDailyTempMorn() {
        return dailyTempMorn;
    }

    public void setDailyTempMorn(Double dailyTempMorn) {
        this.dailyTempMorn = dailyTempMorn;
    }

    public Double getDailyTempDay() {
        return dailyTempDay;
    }

    public void setDailyTempDay(Double dailyTempDay) {
        this.dailyTempDay = dailyTempDay;
    }

    public Double getDailyTempEve() {
        return dailyTempEve;
    }

    public void setDailyTempEve(Double dailyTempEve) {
        this.dailyTempEve = dailyTempEve;
    }

    public Double getDailyTempNight() {
        return dailyTempNight;
    }

    public void setDailyTempNight(Double dailyTempNight) {
        this.dailyTempNight = dailyTempNight;
    }

    public Double getDailyHumidity() {
        return dailyHumidity;
    }

    public void setDailyHumidity(Double dailyHumidity) {
        this.dailyHumidity = dailyHumidity;
    }

    public Double getDailyWindSpeed() {
        return dailyWindSpeed;
    }

    public void setDailyWindSpeed(Double dailyWindSpeed) {
        this.dailyWindSpeed = dailyWindSpeed;
    }

    public Double getDailyClouds() {
        return dailyClouds;
    }

    public void setDailyClouds(Double dailyClouds) {
        this.dailyClouds = dailyClouds;
    }

    public Double getDailyPop() {
        return dailyPop;
    }

    public void setDailyPop(Double dailyPop) {
        this.dailyPop = dailyPop;
    }

    public String getDailyWeatherDescription() {
        return dailyWeatherDescription;
    }

    public void setDailyWeatherDescription(String dailyWeatherDescription) {
        this.dailyWeatherDescription = dailyWeatherDescription;
    }

    public String getDailyWeatherIcon() {
        return dailyWeatherIcon;
    }

    public void setDailyWeatherIcon(String dailyWeatherIcon) {
        this.dailyWeatherIcon = dailyWeatherIcon;
    }

    public Double getDailyTempMin() {
        return dailyTempMin;
    }

    public void setDailyTempMin(Double dailyTempMin) {
        this.dailyTempMin = dailyTempMin;
    }

    public Double getDailyTempMax() {
        return dailyTempMax;
    }

    public void setDailyTempMax(Double dailyTempMax) {
        this.dailyTempMax = dailyTempMax;
    }

    @Override
    public String toString() {
        return "WeatherSevenDay{" +
                "date=" + date +
                ", dailyTempMin=" + dailyTempMin +
                ", dailyTempMax=" + dailyTempMax +
                ", dailyTempMorn=" + dailyTempMorn +
                ", dailyTempDay=" + dailyTempDay +
                ", dailyTempEve=" + dailyTempEve +
                ", dailyTempNight=" + dailyTempNight +
                ", dailyHumidity=" + dailyHumidity +
                ", dailyWindSpeed=" + dailyWindSpeed +
                ", dailyClouds=" + dailyClouds +
                ", dailyPop=" + dailyPop +
                ", dailyWeatherDescription='" + dailyWeatherDescription + '\'' +
                ", dailyWeatherIcon=" + dailyWeatherIcon +
                '}';
    }
}
