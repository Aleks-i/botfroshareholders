package botforshareholders.handler;

import botforshareholders.bot.Bot;
import botforshareholders.command.ParsedCommand;
import botforshareholders.keyboard.WeatherKeyboard;
import botforshareholders.model.WeatherNow;
import botforshareholders.model.WeatherSevenDay;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static botforshareholders.Security.getApiWeatherToken;

@Component
public class CallbackWeatherHandler extends AbstractHandler {
    private static final Logger log = LoggerFactory.getLogger(WeatherHandler.class);
    private final WeatherNow weatherModelNow;

    public CallbackWeatherHandler(Bot bot, WeatherNow weatherModelNow, WeatherKeyboard weatherKeyboard) {
        super(bot);
        this.weatherModelNow = weatherModelNow;
    }

    @Override
    public String operate(String chatId, ParsedCommand parsedCommand, Update update) {
        Bot.sendQueue.add(getWeater(update));
        return "";
    }

    public SendMessage getWeater(Update update) {
        String[] strings = update.getCallbackQuery().getData().split("_");
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
        switch (strings[0]) {
            case "1" -> sendMessage.setText(getWeatherForOneDay(strings[1]));
            case "2" -> sendMessage.setText(getWeatherForSevenDay(strings[1], strings[2], strings[3]));
            case "3" -> sendMessage.setText("гри, чо добавить");
        }
        return sendMessage;
    }

    public String getWeatherForOneDay(String message) {
        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + message + "&units=metric&appid=" + getApiWeatherToken() + "&lang=ru");
            try (Scanner in = new Scanner((InputStream) url.getContent())) {
                StringBuilder result = new StringBuilder();

                while (in.hasNext()) {
                    result.append(in.nextLine().replaceAll("_", " "));
                }
                JSONObject object = new JSONObject(result.toString());
                weatherModelNow.setNameCity(object.getString("name"));

                JSONObject objectMain = object.getJSONObject("main");
                weatherModelNow.setTemp(objectMain.getDouble("temp"));
                weatherModelNow.setFeelsLikeTemp(objectMain.getDouble("feels like"));
                weatherModelNow.setHumidity(objectMain.getDouble("humidity"));

                JSONObject objectWind = object.getJSONObject("wind");
                weatherModelNow.setSpeedWind(objectWind.getDouble("speed"));

                JSONObject objectClouds = object.getJSONObject("clouds");
                weatherModelNow.setCloudiness(objectClouds.getDouble("all"));

                JSONArray jsonArray = object.getJSONArray("weather");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    weatherModelNow.setIcon((String) obj.get("icon"));
                    weatherModelNow.setDescription(obj.getString("description"));
                }
            }
        } catch (IOException e) {
            log.info("https://api.openweathermap.org/data/2.5/weather?q=" + message + "&units=metric&appid=" +
                    getApiWeatherToken() + "&lang=ru недоступен");
        }
        return weatherModelNow.toString();
    }

    public String getWeatherForSevenDay(String lat, String lon, String city) {
        String timeZone = "";
        List<WeatherSevenDay> weatherSevenDayList = new ArrayList<>();
        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/onecall?lat=" + lat + "&lon=" + lon + "&exclude=hourly,current&units=metric&appid=" + getApiWeatherToken() + "&lang=ru");
            try (Scanner in = new Scanner((InputStream) url.getContent())) {
                StringBuilder result = new StringBuilder();

                while (in.hasNext()) {
                    result.append(in.nextLine().replaceAll("_", " "));
                }

                JSONObject object = new JSONObject(result.toString());
                timeZone = object.getString("timezone");
                JSONArray jsonArray = object.getJSONArray("daily");
                LocalDate localDate = LocalDate.now();
                for (Object obj : jsonArray) {
                    weatherSevenDayList.add(getWeatherOneDay((JSONObject) obj, localDate));
                    localDate = localDate.plus(1, ChronoUnit.DAYS);
                }
            }
        } catch (IOException e) {
            log.info("https://api.openweathermap.org/data/2.5/onecall?lat=" + lat + "&lon=" + lon + "&exclude=hourly&units=metric&appid=" + getApiWeatherToken() + "&lang=ru недоступен");
        }
        return modifiListWithWeaterToSting(weatherSevenDayList, city);
    }

    private WeatherSevenDay getWeatherOneDay(JSONObject jsonObject, LocalDate localDate) {
        WeatherSevenDay weatherSevenDay = new WeatherSevenDay(localDate);
        weatherSevenDay.setDailyHumidity(jsonObject.getDouble("humidity"));
        weatherSevenDay.setDailyWindSpeed(jsonObject.getDouble("wind speed"));
        weatherSevenDay.setDailyClouds(jsonObject.getDouble("clouds"));
        weatherSevenDay.setDailyPop(jsonObject.getDouble("pop"));

        JSONObject temp = jsonObject.getJSONObject("temp");
        weatherSevenDay.setDailyTempMin(temp.getDouble("min"));
        weatherSevenDay.setDailyTempMax(temp.getDouble("max"));
        weatherSevenDay.setDailyTempEve(temp.getDouble("eve"));
        weatherSevenDay.setDailyTempNight(temp.getDouble("night"));
        weatherSevenDay.setDailyTempDay(temp.getDouble("day"));
        weatherSevenDay.setDailyTempMorn(temp.getDouble("morn"));

        JSONArray jsonArray = jsonObject.getJSONArray("weather");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            weatherSevenDay.setDailyWeatherDescription(object.getString("description"));
            weatherSevenDay.setDailyWeatherIcon(object.getString("icon"));
        }
        return weatherSevenDay;
    }

    private String modifiListWithWeaterToSting(List<WeatherSevenDay> weatherSevenDayList, String timeZone) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(timeZone).append("\n");
        for (WeatherSevenDay sevenDay : weatherSevenDayList) {
            stringBuilder.append(sevenDay.getDate()).append("\n");
            stringBuilder.append("t min/max    ").append(sevenDay.getDailyTempMin()).append(" / ").append(sevenDay.getDailyTempMax()).append(" C\n");
            stringBuilder.append("влажность/ветер    ").append(sevenDay.getDailyHumidity()).append(" %").append(" / ").append(sevenDay.getDailyWindSpeed()).append(" м/с\n");
            stringBuilder.append(sevenDay.getDailyWeatherDescription()).append("\n");
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
