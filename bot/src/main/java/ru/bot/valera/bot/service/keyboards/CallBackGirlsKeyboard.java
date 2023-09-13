package ru.bot.valera.bot.service.keyboards;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.bot.valera.bot.model.Command;

import java.util.ArrayList;
import java.util.List;

@Component
public class CallBackGirlsKeyboard implements Keyboard {
    public ReplyKeyboard getKeyboard(long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> inlinekeyboardRowFirst = new ArrayList<>();
        inlinekeyboardRowFirst.add(getInlineKeyboardButton("титечные гифки", String.valueOf(Command.TITS_GIF)));
        inlinekeyboardRowFirst.add(getInlineKeyboardButton("титечные видосики", String.valueOf(Command.TITS_VIDEO)));

        List<InlineKeyboardButton> inlinekeyboardRowSecond = new ArrayList<>();
        inlinekeyboardRowSecond.add(getInlineKeyboardButton("красотки", String.valueOf(Command.GIRLS_VIDEO)));

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