package ru.bot.valera.bot.service.keyboards.weathher;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.service.keyboards.Keyboard;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.bot.valera.bot.util.WeatherUtil.CITIES_NAMES;
import static ru.bot.valera.bot.util.WeatherUtil.coordsList;

@Component
public class CallBackWeatherKeyboardOneDay implements Keyboard {
    public ReplyKeyboard getKeyboard(long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardRaws = new ArrayList<>();
        List<InlineKeyboardButton>[] inlinekeyboardRow = new List[]{new ArrayList<>()};

        AtomicInteger counter = new AtomicInteger();
        AtomicInteger idxCity = new AtomicInteger(0);
        coordsList.forEach(coord -> {
            String sityName = CITIES_NAMES.get(coord);

            if (counter.get() != 3) {
                counter.getAndIncrement();
                inlinekeyboardRow[0].add(getInlineKeyboardButton(sityName, "-4-" + idxCity.getAndIncrement()));
            } else {
                inlineKeyboardRaws.add(inlinekeyboardRow[0]);
                inlinekeyboardRow[0] = new ArrayList<>();
                inlinekeyboardRow[0].add(getInlineKeyboardButton(sityName, "-4-" + idxCity.getAndIncrement()));
                counter.set(1);
            }
        });
        inlineKeyboardRaws.add(inlinekeyboardRow[0]);

        inlinekeyboardRow[0] = new ArrayList<>();
        inlinekeyboardRow[0].add(getInlineKeyboardButton("Назад", "-3" ));
        inlineKeyboardRaws.add(inlinekeyboardRow[0]);

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
