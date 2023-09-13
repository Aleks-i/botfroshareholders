package ru.bot.valera.bot.service.keyboards;

import com.vdurmont.emoji.EmojiManager;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.EmojiEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ru.bot.valera.bot.bot.Bot.CHAT_STORAGE;
import static ru.bot.valera.bot.model.EmojiEnum.DISABLE;
import static ru.bot.valera.bot.model.EmojiEnum.ENABLE;

@Component
public class CallBackSettingsKeyboard implements Keyboard {

    @Override
    public InlineKeyboardMarkup getKeyboard(long chatId) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardRaws = new ArrayList<>();
        List<InlineKeyboardButton> inlinekeyboardRow = new ArrayList<>();
        Map<Command, Boolean> mailerMap = CHAT_STORAGE.get(chatId).getMailerMap();

        int counter = 0;
        for (Command command : Command.values()) {
            if (!command.getTitle().isEmpty()) {
                if (counter % 3 == 0 && counter != 0) {
                    inlineKeyboardRaws.add(inlinekeyboardRow);
                    inlinekeyboardRow = new ArrayList<>();
                }
                inlinekeyboardRow.add(getInlineKeyboardButton(getEmoji(mailerMap.get(command)) + " " + command.getTitle(),
                        Command.CALLBACK_SETTINGS + "-" + command.name()));
                counter++;
            }
        }
        if (!inlinekeyboardRow.isEmpty()) {
            inlineKeyboardRaws.add(inlinekeyboardRow);
        }

        //TODO add payments
//        inlinekeyboardRow = new ArrayList<>();
//        inlinekeyboardRow.add(getInlineKeyboardButton(EmojiManager.getForAlias(MONEY.getTitle()).getUnicode()
//                        + " Поддержка Отечественным Рублем", PAYMENT.name()));
//        inlineKeyboardRaws.add(inlinekeyboardRow);

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardRaws);
        return inlineKeyboardMarkup;
    }

    private InlineKeyboardButton getInlineKeyboardButton(String text, String callBackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callBackData);
        return button;
    }

    private String getEmoji(boolean flag) {
        EmojiEnum emoji = flag ? ENABLE : DISABLE;
        return EmojiManager.getForAlias(emoji.getTitle()).getUnicode();
    }
}
