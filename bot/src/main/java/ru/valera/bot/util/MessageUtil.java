package ru.valera.bot.util;

import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import ru.valera.bot.model.Content;
import ru.valera.bot.service.keyboard.MainKeyboard;
import ru.valera.bot.to.UpdateTO;

import java.util.concurrent.ThreadLocalRandom;

@UtilityClass
public class MessageUtil {

    public static Content getContent(String text, UpdateTO updateTO, MainKeyboard keyboard) {
        return new Content(setParamMessage(getSendMessage(text, keyboard), updateTO), updateTO.getMessageType());
    }

    public static Content getContent(String text, UpdateTO updateTO) {
        return new Content(setParamMessage(getSendMessage(text), updateTO), updateTO.getMessageType());
    }

    public static SendMessage getSendMessage(String text, MainKeyboard keyboard) {
        return keyboard.setReplyKeyboardMarkupMain(getSendMessage(text));
    }

    public static SendMessage getSendMessage(String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        return sendMessage;
    }

    public static SendMessage setParamMessage(SendMessage sendMessageTemplate, UpdateTO updateTO) {
        sendMessageTemplate.enableMarkdown(true);
        sendMessageTemplate.setChatId(updateTO.getChatId());

        int messageId = updateTO.getMessageId();
        if (messageId != 0) {
            sendMessageTemplate.setReplyToMessageId(updateTO.getMessageId());
        }
        return sendMessageTemplate;
    }

    public static SendAnimation setParamMessage(SendAnimation sendAnimation, UpdateTO updateTO) {
        sendAnimation.setChatId(updateTO.getChatId());
        sendAnimation.setReplyToMessageId(updateTO.getMessageId());
        return sendAnimation;
    }

    public static SendVideo setParamMessage(SendVideo sendVideo, UpdateTO updateTO) {
        sendVideo.setChatId(updateTO.getChatId());
        sendVideo.setReplyToMessageId(updateTO.getMessageId());
        return sendVideo;
    }

    public static int getRandomListIdx(int listSize) {
        return ThreadLocalRandom.current().nextInt(0, listSize);
    }
}
