package botforshareholders.util;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class Keyboard {

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

    public void setReplyKeyboardMarkupWeather(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardRowFirst = new KeyboardRow();
        KeyboardRow keyboardRowSecond = new KeyboardRow();
        KeyboardRow keyboardRowThird = new KeyboardRow();
        KeyboardRow keyboardRowFourth = new KeyboardRow();
        KeyboardRow keyboardRowFifth = new KeyboardRow();
        KeyboardRow keyboardRowSixth = new KeyboardRow();

        keyboardRowFirst.add(new KeyboardButton("/Екатеринбург"));
        keyboardRowFirst.add(new KeyboardButton("/Екатеринбург на 7 дней"));
        keyboardRowSecond.add(new KeyboardButton("/Москва"));
        keyboardRowSecond.add(new KeyboardButton("/Москва на 7 дней"));
        keyboardRowThird.add(new KeyboardButton("/Тагииииил"));
        keyboardRowThird.add(new KeyboardButton("/Тагил на 7 дней"));
        keyboardRowFourth.add(new KeyboardButton("/Торжок"));
        keyboardRowFourth.add(new KeyboardButton("/Торжок на 7 дней"));
        keyboardRowFifth.add(new KeyboardButton("/Сочи"));
        keyboardRowFifth.add(new KeyboardButton("/Хуёчи на 7 дней"));
        keyboardRowSixth.add(new KeyboardButton("/добавь еще этот город..."));

        keyboardRowList.add(keyboardRowFirst);
        keyboardRowList.add(keyboardRowSecond);
        keyboardRowList.add(keyboardRowThird);
        keyboardRowList.add(keyboardRowFourth);
        keyboardRowList.add(keyboardRowFifth);
        keyboardRowList.add(keyboardRowSixth);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }

    public void setInlineKeyboardMarkupWeather(SendMessage sendMessage) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        List<List<InlineKeyboardButton>> inlineKeyboardRaws = new ArrayList<>();

        List<InlineKeyboardButton> inlinekeyboardRowFirst = new ArrayList<>();
        inlinekeyboardRowFirst.add(new InlineKeyboardButton().setText("Екатеринбург").setCallbackData("ekbweather"));
        inlinekeyboardRowFirst.add(new InlineKeyboardButton().setText("Москва").setCallbackData("mskweather"));

        List<InlineKeyboardButton> inlinekeyboardRowSecond = new ArrayList<>();
        inlinekeyboardRowFirst.add(new InlineKeyboardButton().setText("Тагииииил").setCallbackData("tagilweather"));
        inlinekeyboardRowFirst.add(new InlineKeyboardButton().setText("Торжок").setCallbackData("torgok"));

        List<InlineKeyboardButton> inlinekeyboardRowThird = new ArrayList<>();
        inlinekeyboardRowFirst.add(new InlineKeyboardButton("/Сочи").setCallbackData("sochi"));
        inlinekeyboardRowFirst.add(new InlineKeyboardButton("/Хуёчи").setCallbackData("huechi"));

        List<InlineKeyboardButton> inlinekeyboardRowFourth = new ArrayList<>();
        inlinekeyboardRowFirst.add(new InlineKeyboardButton("/добавь еще этот город...").setCallbackData("what?"));

        inlineKeyboardRaws.add(inlinekeyboardRowFirst);
        inlineKeyboardRaws.add(inlinekeyboardRowSecond);
        inlineKeyboardRaws.add(inlinekeyboardRowThird);
        inlineKeyboardRaws.add(inlinekeyboardRowFourth);
        inlineKeyboardMarkup.setKeyboard(inlineKeyboardRaws);
    }
}
