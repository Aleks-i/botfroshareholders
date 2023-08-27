package ru.valera.bot.service.handler.weather;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.valera.bot.model.Content;
import ru.valera.bot.model.weather.CurrentWeather;
import ru.valera.bot.model.weather.WeatherSevenDays;
import ru.valera.bot.service.handler.Handler;
import ru.valera.bot.to.MessageType;
import ru.valera.bot.to.UpdateTO;
import ru.valera.bot.util.JsonUtil;
import ru.valera.bot.util.MessageUtil;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherCallback implements Handler {

    @Value("${tokens.weather_token_api}")
    private String weatherToken;

    @Override
    public Content handle(UpdateTO updateTO) {
        SendMessage sendMessage = getWeather(updateTO.getCallBackData());
        return new Content(MessageUtil.setParamMessage(sendMessage, updateTO), updateTO.getMessageType());
    }

    public SendMessage getWeather(String[] callBackData) {
        SendMessage sendMessage = new SendMessage();
        switch (callBackData[0]) {
            case "1" -> sendMessage.setText(getWeatherForOneDay(callBackData[1]));
            case "2" -> sendMessage.setText(getWeatherForSevenDay(callBackData[1], callBackData[2], callBackData[3]));
        }
        return sendMessage;
    }

    public String getWeatherForOneDay(String message) {
        CurrentWeather currentWeather = JsonUtil.readValue("https://api.openweathermap.org/data/2.5/weather?q=" + message + "&units=metric&appid=" +
                weatherToken + "&lang=ru", CurrentWeather.class);
        if (currentWeather == null) {
            return "Сервис погода недоступен. Чиним.";
        }
        return currentWeather.toString();
    }

    public String getWeatherForSevenDay(String lat, String lon, String city) {

        WeatherSevenDays weatherSevenDays = JsonUtil.readValue("https://api.openweathermap.org/data/2.5/onecall?lat=" + lat + "&lon=" + lon +
                "&exclude=hourly,current&units=metric&appid=" + weatherToken + "&lang=ru", WeatherSevenDays.class);

        if (weatherSevenDays == null) {
            return "Сервис погода недоступен. Чиним.";
        }

        return weatherSevenDays.toString();
    }

    @Override
    public Set<MessageType> getMessageType() {
        return Set.of(MessageType.CALLBACK_WEATHER);
    }
}
