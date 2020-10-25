package botforshareholders.handler;

import botforshareholders.bot.Bot;
import botforshareholders.command.Command;
import botforshareholders.command.ParsedCommand;
import botforshareholders.model.WeatherNow;
import botforshareholders.model.WeatherSevenDay;
import botforshareholders.util.Keyboard;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static botforshareholders.Security.apiWeatherToken;
import static botforshareholders.command.Parser.getWordsFromMessage;
import static botforshareholders.util.WeatherUtil.*;

@Component
public class WeatherHandler extends AbstractHandler {
    private static final Logger log = LoggerFactory.getLogger(WeatherHandler.class);
    private final WeatherNow weatherModelNow;

    private final Keyboard keyboard;

    public WeatherHandler(Bot bot, WeatherNow weatherModelNow, Keyboard keyboard) {
        super(bot);
        this.weatherModelNow = weatherModelNow;
        this.keyboard = keyboard;
    }

    @Override
    public String operate(String chatId, ParsedCommand parsedCommand, Update update) {
        Command command = parsedCommand.getCommand();
        String text = update.getMessage().getText();

        if (text.equalsIgnoreCase("погода")) {
            bot.sendQueue.add(getMessageWithKeyboardWeatherOneDay(update.getMessage()));
        } else bot.sendQueue.add(getMessageWithKeyboardWeatherOneDay(update.getMessage()));

        return "";
    }

    public SendMessage getMessageWithKeyboardWeatherOneDay(Message message) {
        List<String> words = getWordsFromMessage(message.getText());
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setReplyToMessageId(message.getMessageId());

        try {
            if (message.getText().equalsIgnoreCase("погода")) {
                keyboard.setReplyKeyboardMarkupWeather(sendMessage);
                return sendMessage.setText("Выбери чо нить");
            } else if (words.contains("/добавь") && words.contains("еще") && words.contains("этот") && words.contains("город")) {
                sendMessage.setText("Какой?");
            } else if (message.getText().equalsIgnoreCase("/Тагииииил")) {
                sendMessage.setText(getWeatherNow("Нижний Тагил"));
            } else if (words.contains("/екатеринбург") && words.contains("на") && words.contains("7") && words.contains("дней")) {
                sendMessage.setText(getWeatherSevenDay(message.getText(), EKB_LAT, EKB_LON));
            } else if (words.contains("/москва") && words.contains("на") && words.contains("7") && words.contains("дней")) {
                sendMessage.setText(getWeatherSevenDay(message.getText(), MSC_LAT, MSC_LON));
            } else if (words.contains("/тагил") && words.contains("на") && words.contains("7") && words.contains("дней")) {
                sendMessage.setText(getWeatherSevenDay(message.getText(), TAGIL_LAT, TAGIL_LON));
            } else if (words.contains("/торжок") && words.contains("на") && words.contains("7") && words.contains("дней")) {
                sendMessage.setText(getWeatherSevenDay(message.getText(), TORGOK_LAT, TORGOK_LON));
            } else if (words.contains("/хуёчи") && words.contains("на") && words.contains("7") && words.contains("дней")) {
                sendMessage.setText(getWeatherSevenDay(message.getText(), SOCHI_LAT, SOCHI_LON));
            } else {
                sendMessage.setText(getWeatherNow(message.getText().replace("/", "")));
            }
        } catch (IOException e) {
            sendMessage.setText("Ну нет такого города, набирай внимательней, итак сплошной спам в группе, ну что ты");
        }
        keyboard.setReplyKeyboardMarkupMain(sendMessage);
        return sendMessage;
    }

    public String getWeatherNow(String message) throws IOException {
        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + message + "&units=metric&appid=" + apiWeatherToken + "&lang=ru");

        Scanner in = new Scanner((InputStream) url.getContent());
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
        return weatherModelNow.toString();
    }

    public String getWeatherSevenDay(String message, String lat, String lon) throws IOException {
        URL url = new URL("https://api.openweathermap.org/data/2.5/onecall?lat=" + lat + "&lon=" + lon + "&exclude=hourly&units=metric&appid=" + apiWeatherToken + "&lang=ru");
        Scanner in = new Scanner((InputStream) url.getContent());
        StringBuilder result = new StringBuilder();

        while (in.hasNext()) {
            result.append(in.nextLine().replaceAll("_", " "));
        }

        JSONObject object = new JSONObject(result.toString());
        String timeZone = object.getString("timezone");
        JSONArray jsonArray = object.getJSONArray("daily");
        List<WeatherSevenDay> weatherSevenDayList = new ArrayList<>();
        LocalDate localDate = LocalDate.now();
        for (Object obj : jsonArray) {
            weatherSevenDayList.add(getWeatherOneDay((JSONObject) obj,localDate));
            localDate = localDate.plus(1, ChronoUnit.DAYS);
        }
        return modifiListWithWeaterToSting(weatherSevenDayList, timeZone);
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
 //       stringBuilder.append(timeZone + "\n");
        for (WeatherSevenDay sevenDay : weatherSevenDayList) {
            stringBuilder.append(sevenDay.getDate() + "\n");
            stringBuilder.append("t min = " + sevenDay.getDailyTempMin() + " C\n");
            stringBuilder.append("t max = " + sevenDay.getDailyTempMax() + " C\n");
            stringBuilder.append("влажность " + sevenDay.getDailyHumidity() + " %\n");
            stringBuilder.append("ветер = " + sevenDay.getDailyWindSpeed() + " м/с\n");
            stringBuilder.append(sevenDay.getDailyWeatherDescription() + "\n");
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}