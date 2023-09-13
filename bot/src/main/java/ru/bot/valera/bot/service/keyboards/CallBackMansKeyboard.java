package ru.bot.valera.bot.service.keyboards;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static ru.bot.valera.bot.model.Command.MANS_GIF;
import static ru.bot.valera.bot.model.Command.MANS_VIDEO;

@Component
public class CallBackMansKeyboard implements Keyboard {
    public ReplyKeyboard getKeyboard(long chatId) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> inlinekeyboardRowFirst = new ArrayList<>();
        inlinekeyboardRowFirst.add(getInlineKeyboardButton("гифки", String.valueOf(MANS_GIF)));
        inlinekeyboardRowFirst.add(getInlineKeyboardButton("видосики", String.valueOf(MANS_VIDEO)));

        List<List<InlineKeyboardButton>> inlineKeyboardRaws = new ArrayList<>();
        inlineKeyboardRaws.add(inlinekeyboardRowFirst);

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
