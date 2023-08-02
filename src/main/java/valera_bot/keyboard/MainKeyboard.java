package valera_bot.keyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class MainKeyboard {

    public void setReplyKeyboardMarkupMain(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardRowFirst = new KeyboardRow();
        KeyboardRow keyboardRowSecond = new KeyboardRow();
        KeyboardRow keyboardRowThird = new KeyboardRow();

        keyboardRowFirst.add(new KeyboardButton("Погода"));
        keyboardRowFirst.add(new KeyboardButton("Что нибудь еще"));
        keyboardRowSecond.add(new KeyboardButton("Курс валют"));
        keyboardRowSecond.add(new KeyboardButton("Голые бабы"));
        keyboardRowThird.add(new KeyboardButton("Чо майнить"));
        keyboardRowThird.add(new KeyboardButton("Замори анекдот"));

        keyboardRowList.add(keyboardRowFirst);
        keyboardRowList.add(keyboardRowSecond);
        keyboardRowList.add(keyboardRowThird);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }
}
