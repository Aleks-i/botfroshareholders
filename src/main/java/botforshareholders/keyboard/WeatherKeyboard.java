package botforshareholders.keyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static botforshareholders.util.WeatherUtil.*;

@Component
public class WeatherKeyboard {
    public void setInlineKeyboardMarkupWeather(SendMessage sendMessage) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        List<InlineKeyboardButton> inlinekeyboardRowFirst = new ArrayList<>();
        inlinekeyboardRowFirst.add(new InlineKeyboardButton().setText("Екб").setCallbackData("1_" + "Екатеринбург"));
        inlinekeyboardRowFirst.add(new InlineKeyboardButton().setText("Екб 8 дней").setCallbackData("2_" + EKB_LAT + "_" + EKB_LON + "_" + "Екатеринбург"));
        inlinekeyboardRowFirst.add(new InlineKeyboardButton().setText("Москва").setCallbackData("1_" + "Москва"));
        inlinekeyboardRowFirst.add(new InlineKeyboardButton().setText("Мск 8 дней").setCallbackData("2_" + MSC_LAT + "_" + MSC_LON + "_" + "Москва"));

        List<InlineKeyboardButton> inlinekeyboardRowSecond = new ArrayList<>();
        inlinekeyboardRowSecond.add(new InlineKeyboardButton("Сочи").setCallbackData("1_" + "Сочи"));
        inlinekeyboardRowSecond.add(new InlineKeyboardButton("Хуёчи 8 дней").setCallbackData("2_" + SOCHI_LAT + "_" + SOCHI_LON + "_" + "Сочи"));

        List<InlineKeyboardButton> inlinekeyboardRowThird = new ArrayList<>();
        inlinekeyboardRowThird.add(new InlineKeyboardButton().setText("Тагииииил").setCallbackData("1_" + "Нижний Тагил"));
        inlinekeyboardRowThird.add(new InlineKeyboardButton().setText("Торжок").setCallbackData("1_" + "Торжок"));

        List<InlineKeyboardButton> inlinekeyboardRowFourth = new ArrayList<>();
        inlinekeyboardRowFourth.add(new InlineKeyboardButton("добавь еще этот город...").setCallbackData("3"));

        List<List<InlineKeyboardButton>> inlineKeyboardRaws = new ArrayList<>();
        inlineKeyboardRaws.add(inlinekeyboardRowFirst);
        inlineKeyboardRaws.add(inlinekeyboardRowSecond);
        inlineKeyboardRaws.add(inlinekeyboardRowThird);
        inlineKeyboardRaws.add(inlinekeyboardRowFourth);
        inlineKeyboardMarkup.setKeyboard(inlineKeyboardRaws);
    }
}