package valera_bot.keyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static valera_bot.util.WeatherUtil.*;

@Component
public class WeatherKeyboard {
    public void setInlineKeyboardMarkupWeather(SendMessage sendMessage) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        List<InlineKeyboardButton> inlinekeyboardRowFirst = new ArrayList<>();
        inlinekeyboardRowFirst.add(getInlineKeyboardButton("Екб", "1_" + "Екатеринбург"));
        inlinekeyboardRowFirst.add(getInlineKeyboardButton("Екб 8 дней", "2_" + EKB_LAT + "_" + EKB_LON + "_" + "Екатеринбург"));
        inlinekeyboardRowFirst.add(getInlineKeyboardButton("Москва", "1_" + "Москва"));
        inlinekeyboardRowFirst.add(getInlineKeyboardButton("Мск 8 дней", "2_" + MSC_LAT + "_" + MSC_LON + "_" + "Москва"));

        List<InlineKeyboardButton> inlinekeyboardRowSecond = new ArrayList<>();
        inlinekeyboardRowSecond.add(getInlineKeyboardButton("Артёмовский", "1_" + "Артёмовский"));
        inlinekeyboardRowSecond.add(getInlineKeyboardButton("Он же на 8 дней", "2_" + ART_LAT + "_" + ART_LON + "_" + "Артёмовский"));

        List<InlineKeyboardButton> inlinekeyboardRowThird = new ArrayList<>();
        inlinekeyboardRowThird.add(getInlineKeyboardButton("Сочи", "1_" + "Сочи"));
        inlinekeyboardRowThird.add(getInlineKeyboardButton("Хуёчи 8 дней", "2_" + SOCHI_LAT + "_" + SOCHI_LON + "_" + "Сочи"));

        List<InlineKeyboardButton> inlinekeyboardRowFourth = new ArrayList<>();
        inlinekeyboardRowFourth.add(getInlineKeyboardButton("Тагииииил", "1_" + "Нижний Тагил"));
        inlinekeyboardRowFourth.add(getInlineKeyboardButton("Торжок", "1_" + "Торжок"));

        List<InlineKeyboardButton> inlinekeyboardRowFifth = new ArrayList<>();
        inlinekeyboardRowFifth.add(getInlineKeyboardButton("добавь еще этот город...", "3"));

        List<List<InlineKeyboardButton>> inlineKeyboardRaws = new ArrayList<>();
        inlineKeyboardRaws.add(inlinekeyboardRowFirst);
        inlineKeyboardRaws.add(inlinekeyboardRowSecond);
        inlineKeyboardRaws.add(inlinekeyboardRowThird);
        inlineKeyboardRaws.add(inlinekeyboardRowFourth);
        inlineKeyboardRaws.add(inlinekeyboardRowFifth);
        inlineKeyboardMarkup.setKeyboard(inlineKeyboardRaws);
    }

    private InlineKeyboardButton getInlineKeyboardButton(String text, String callBackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callBackData);
        return button;
    }
}