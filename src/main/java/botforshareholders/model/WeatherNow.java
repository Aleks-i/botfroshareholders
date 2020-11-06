package botforshareholders.model;

import org.springframework.stereotype.Component;

@Component
public class WeatherNow {
    private String nameCity;
    private Double temp;
    private Double feelsLikeTemp;
    private Double humidity;
    private String description;
    private Double cloudiness;
    private Double speedWind;
    private String icon;


    public String getNameCity() {
        return nameCity;
    }

    public void setNameCity(String nameCity) {
        this.nameCity = nameCity;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getFeelsLikeTemp() {
        return feelsLikeTemp;
    }

    public void setFeelsLikeTemp(Double feelsLikeTemp) {
        this.feelsLikeTemp = feelsLikeTemp;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getSpeedWind() {
        return speedWind;
    }

    public void setSpeedWind(Double speedWind) {
        this.speedWind = speedWind;
    }

    public Double getCloudiness() {
        return cloudiness;
    }

    public void setCloudiness(Double cloudiness) {
        this.cloudiness = cloudiness;
    }

    @Override
    public String toString() {
        return  "Город: " + nameCity + "\n"
                + "температура: " + temp + "C" + "\n"
                + "по ощущениям: " + feelsLikeTemp + "C" + "\n"
                + "скорость ветра: " + speedWind + "м/с" + "\n"
                + "влажность: " + humidity + "%" + "\n"
                + "описание: " + description + "\n"
                + "облачность: " + cloudiness + "%" + "\n"
//               + "http://openweathermap.org/img/wn/" + icon + ".png"
                ;
    }
}
