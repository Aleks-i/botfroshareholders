package ru.bot.valera.bot.service.keyboards;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.bot.valera.bot.model.Command;

import java.util.ArrayList;
import java.util.List;

@Component
public class CallBackMiningKeyboard implements Keyboard {
    public ReplyKeyboard getKeyboard(long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> inlinekeyboardRowFirst = new ArrayList<>();
        inlinekeyboardRowFirst.add(getInlineKeyboardButton("1080", "-1080" ));
        inlinekeyboardRowFirst.add(getInlineKeyboardButton("1080ti", "-1080ti" ));
        inlinekeyboardRowFirst.add(getInlineKeyboardButton("2080", "-2080" ));
        inlinekeyboardRowFirst.add(getInlineKeyboardButton("2080ti", "-2080ti" ));

        List<InlineKeyboardButton> inlinekeyboardRowSecond = new ArrayList<>();
        inlinekeyboardRowSecond.add(getInlineKeyboardButton("3060", "-3060" ));
        inlinekeyboardRowSecond.add(getInlineKeyboardButton("3070", "-3070" ));
        inlinekeyboardRowSecond.add(getInlineKeyboardButton("3080", "-3080" ));
        inlinekeyboardRowSecond.add(getInlineKeyboardButton("3090", "-3090" ));

        List<InlineKeyboardButton> inlinekeyboardRowThird = new ArrayList<>();
        inlinekeyboardRowThird.add(getInlineKeyboardButton("3060ti", "-3060ti" ));
        inlinekeyboardRowThird.add(getInlineKeyboardButton("3070ti", "-3070ti" ));
        inlinekeyboardRowThird.add(getInlineKeyboardButton("3080ti", "-3080ti" ));
        inlinekeyboardRowThird.add(getInlineKeyboardButton("3090ti", "-3090ti" ));

        List<InlineKeyboardButton> inlinekeyboardRowFourth = new ArrayList<>();
        inlinekeyboardRowFourth.add(getInlineKeyboardButton("4060", "-4060" ));
        inlinekeyboardRowFourth.add(getInlineKeyboardButton("4070", "-4070" ));
        inlinekeyboardRowFourth.add(getInlineKeyboardButton("4080", "-4080" ));
        inlinekeyboardRowFourth.add(getInlineKeyboardButton("4090", "-4090" ));

        List<InlineKeyboardButton> inlinekeyboardRowFifth = new ArrayList<>();
        inlinekeyboardRowFifth.add(getInlineKeyboardButton("4060ti", "-4060ti" ));
        inlinekeyboardRowFifth.add(getInlineKeyboardButton("4070ti", "-4070ti" ));

        List<List<InlineKeyboardButton>> inlineKeyboardRaws = new ArrayList<>();
        inlineKeyboardRaws.add(inlinekeyboardRowFirst);
        inlineKeyboardRaws.add(inlinekeyboardRowSecond);
        inlineKeyboardRaws.add(inlinekeyboardRowThird);
        inlineKeyboardRaws.add(inlinekeyboardRowFourth);
        inlineKeyboardRaws.add(inlinekeyboardRowFifth);
        inlineKeyboardMarkup.setKeyboard(inlineKeyboardRaws);

        return inlineKeyboardMarkup;
    }

    private InlineKeyboardButton getInlineKeyboardButton(String text, String callBackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(Command.CALLBACK_MINING + callBackData);
        return button;
    }
}