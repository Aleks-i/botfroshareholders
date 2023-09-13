package ru.bot.valera.bot.service.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public interface Keyboard {

    ReplyKeyboard getKeyboard(long chatId);
}
