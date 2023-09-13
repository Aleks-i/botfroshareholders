package ru.bot.valera.bot.service.keyboards.weathher;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.service.keyboards.Keyboard;

import java.util.ArrayList;
import java.util.List;

@Component
public class CallBackWeatherKeyboardStart implements Keyboard {

    @Override
    public ReplyKeyboard getKeyboard(long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> inlineKeyboardRowFirst = new ArrayList<>();
        inlineKeyboardRowFirst.add(getInlineKeyboardButton("Текущая погода", "-1" ));
        inlineKeyboardRowFirst.add(getInlineKeyboardButton("Прогноз на 8 дней", "-2" ));

        List<List<InlineKeyboardButton>> inlineKeyboardRaws = new ArrayList<>();
        inlineKeyboardRaws.add(inlineKeyboardRowFirst);
        inlineKeyboardMarkup.setKeyboard(inlineKeyboardRaws);

        return inlineKeyboardMarkup;
    }

    private InlineKeyboardButton getInlineKeyboardButton(String text, String callBackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(Command.EDIT_WEATHER_KEYBOARD + callBackData);
        return button;
    }
}
