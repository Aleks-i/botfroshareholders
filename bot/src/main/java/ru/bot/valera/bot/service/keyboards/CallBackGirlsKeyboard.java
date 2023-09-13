package ru.bot.valera.bot.service.keyboards;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.bot.valera.bot.model.Command;

import java.util.ArrayList;
import java.util.List;

import static ru.bot.valera.bot.model.Command.*;

@Component
public class CallBackGirlsKeyboard implements Keyboard {
    public ReplyKeyboard getKeyboard(long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> inlinekeyboardRowFirst = new ArrayList<>();
        inlinekeyboardRowFirst.add(getInlineKeyboardButton(TITS_GIF.getTitle(), String.valueOf(TITS_GIF)));
        inlinekeyboardRowFirst.add(getInlineKeyboardButton(TITS_VIDEO.getTitle(), String.valueOf(TITS_VIDEO)));

        List<InlineKeyboardButton> inlinekeyboardRowSecond = new ArrayList<>();
        inlinekeyboardRowSecond.add(getInlineKeyboardButton(GIRLS_VIDEO.getTitle(), String.valueOf(GIRLS_VIDEO)));

        List<List<InlineKeyboardButton>> inlineKeyboardRaws = new ArrayList<>();
        inlineKeyboardRaws.add(inlinekeyboardRowFirst);
        inlineKeyboardRaws.add(inlinekeyboardRowSecond);

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardRaws);

        return inlineKeyboardMarkup;
    }

    private InlineKeyboardButton getInlineKeyboardButton(String text, String callBackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callBackData);
        return button;
    }
}