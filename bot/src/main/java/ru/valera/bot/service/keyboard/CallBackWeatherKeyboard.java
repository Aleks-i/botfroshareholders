package ru.valera.bot.service.keyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.valera.bot.to.MessageType;
import ru.valera.bot.util.WeatherUtil;

import java.util.ArrayList;
import java.util.List;

@Component
public class CallBackWeatherKeyboard {
    public InlineKeyboardMarkup getInlineKeyboardMarkupWeather() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> inlinekeyboardRowFirst = new ArrayList<>();
        inlinekeyboardRowFirst.add(getInlineKeyboardButton("Екб", "-1-" + "Екатеринбург"));
        inlinekeyboardRowFirst.add(getInlineKeyboardButton("Екб 8 дней", "-2-" + WeatherUtil.EKB_LAT + "-" + WeatherUtil.EKB_LON + "-" + "Екатеринбург"));
        inlinekeyboardRowFirst.add(getInlineKeyboardButton("Москва", "-1-" + "Москва"));
        inlinekeyboardRowFirst.add(getInlineKeyboardButton("Мск 8 дней", "-2-" + WeatherUtil.MSC_LAT + "-" + WeatherUtil.MSC_LON + "-" + "Москва"));

        List<InlineKeyboardButton> inlinekeyboardRowSecond = new ArrayList<>();
        inlinekeyboardRowSecond.add(getInlineKeyboardButton("Сургут", "-1-" + "Сургут"));
        inlinekeyboardRowSecond.add(getInlineKeyboardButton("Сургут на 8 дней", "-2-" + WeatherUtil.SURG_LAT + "-" + WeatherUtil.SURG_LON + "-" + "Сургут"));

        List<InlineKeyboardButton> inlinekeyboardRowThird = new ArrayList<>();
        inlinekeyboardRowThird.add(getInlineKeyboardButton("Сочи", "-1-" + "Сочи"));
        inlinekeyboardRowThird.add(getInlineKeyboardButton("Сочи 8 дней", "-2-" + WeatherUtil.SOCHI_LAT + "-" + WeatherUtil.SOCHI_LON + "-" + "Сочи"));

//        List<InlineKeyboardButton> inlinekeyboardRowFourth = new ArrayList<>();
//        inlinekeyboardRowFourth.add(getInlineKeyboardButton("Тагииииил", "-1-" + "Нижний Тагил"));
//        inlinekeyboardRowFourth.add(getInlineKeyboardButton("Торжок", "-1-" + "Торжок"));
//
//        List<InlineKeyboardButton> inlinekeyboardRowFifth = new ArrayList<>();
//        inlinekeyboardRowFifth.add(getInlineKeyboardButton("добавь еще этот город...", "3"));

        List<List<InlineKeyboardButton>> inlineKeyboardRaws = new ArrayList<>();
        inlineKeyboardRaws.add(inlinekeyboardRowFirst);
        inlineKeyboardRaws.add(inlinekeyboardRowSecond);
        inlineKeyboardRaws.add(inlinekeyboardRowThird);
//        inlineKeyboardRaws.add(inlinekeyboardRowFourth);
//        inlineKeyboardRaws.add(inlinekeyboardRowFifth);
        inlineKeyboardMarkup.setKeyboard(inlineKeyboardRaws);

        return inlineKeyboardMarkup;
    }

    private InlineKeyboardButton getInlineKeyboardButton(String text, String callBackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(MessageType.CALLBACK_WEATHER + callBackData);
        return button;
    }
}