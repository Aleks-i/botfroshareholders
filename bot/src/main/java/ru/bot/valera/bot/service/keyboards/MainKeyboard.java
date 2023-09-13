package ru.bot.valera.bot.service.keyboards;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class MainKeyboard implements Keyboard {

    public ReplyKeyboard getKeyboard(long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow rowFirst = new KeyboardRow();
        KeyboardRow rowSecond = new KeyboardRow();
        KeyboardRow rowThird = new KeyboardRow();
        KeyboardRow rowFourth = new KeyboardRow();

        rowFirst.add(new KeyboardButton("Погода" ));
        rowFirst.add(new KeyboardButton("Чё помайнить" ));

        rowSecond.add(new KeyboardButton("Статусы вконтакте" ));
        rowSecond.add(new KeyboardButton("Замори анекдот" ));

        rowThird.add(new KeyboardButton("Курс валют" ));
        rowThird.add(new KeyboardButton("Девочки" ));

        rowFourth.add(new KeyboardButton("Горячие самсы" ));
        rowFourth.add(new KeyboardButton("Настройки бота и рассылки" ));

        keyboardRowList.add(rowFirst);
        keyboardRowList.add(rowSecond);
        keyboardRowList.add(rowThird);
        keyboardRowList.add(rowFourth);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

        return replyKeyboardMarkup;
    }
}
