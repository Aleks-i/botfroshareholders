package ru.bot.valera.bot.service.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.service.keyboards.Keyboard;
import ru.bot.valera.bot.to.UpdateTO;

import java.io.File;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class AbstractContent implements Handler {

    public Content getVideoContent(File file, UpdateTO updateTO) {
        SendVideo sendVideo = new SendVideo(String.valueOf(updateTO.getChatId()), new InputFile(file));
        sendVideo.setReplyToMessageId(updateTO.getMessageId());
        return new Content(sendVideo, updateTO.getCommand());
    }

    public Content getVideoContent(File file, UpdateTO updateTO, String text) {
        SendVideo sendVideo = new SendVideo(String.valueOf(updateTO.getChatId()), new InputFile(file));
        sendVideo.setCaption(text);
        SendVideo.builder().parseMode("Markdown" );
        return new Content(sendVideo, updateTO.getCommand());
    }

    public Content getAnimationContent(File file, UpdateTO updateTO) {
        SendAnimation sendAnimation = new SendAnimation(String.valueOf(updateTO.getChatId()), new InputFile(file));
        sendAnimation.setReplyToMessageId(updateTO.getMessageId());
        return new Content(sendAnimation, updateTO.getCommand());
    }

    public Content getAnimationContent(File file, UpdateTO updateTO, String text) {
        SendAnimation sendAnimation = new SendAnimation(String.valueOf(updateTO.getChatId()), new InputFile(file));
        sendAnimation.setCaption(text);
        return new Content(sendAnimation, updateTO.getCommand());
    }

    public Content getContent(String text, UpdateTO updateTO, Keyboard keyboard) {
        return new Content(setParamMessage(getSendMessage(text, keyboard, updateTO.getChatId()), updateTO.getChatId(), updateTO.getMessageId()),
                updateTO.getCommand());
    }

    public Content getContent(UpdateTO updateTO, Keyboard keyboard) {
        return new Content(getEditMessageReplyMarkup(keyboard, updateTO), updateTO.getCommand());
    }

    public Content getContent(String text, UpdateTO updateTO) {
        return new Content(setParamMessage(getSendMessage(text), updateTO.getChatId(), updateTO.getMessageId()),
                updateTO.getCommand());
    }

    public SendMessage getSendMessage(String text, Keyboard keyboard, long chatId) {
        SendMessage sendMessage = getSendMessage(text);
        sendMessage.setReplyMarkup(keyboard.getKeyboard(chatId));
        return sendMessage;
    }

    public SendMessage getSendMessage(String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        return sendMessage;
    }

    public EditMessageReplyMarkup getEditMessageReplyMarkup(Keyboard keyboard, UpdateTO updateTO) {
        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();

        long chatId = updateTO.getChatId();
        editMessageReplyMarkup.setChatId(chatId);
        editMessageReplyMarkup.setMessageId(updateTO.getMessageId());
        editMessageReplyMarkup.setReplyMarkup((InlineKeyboardMarkup) keyboard.getKeyboard(chatId));
        return editMessageReplyMarkup;
    }

    public SendMessage setParamMessage(SendMessage sendMessageTemplate, long chatId, int messageId) {
        sendMessageTemplate.enableMarkdown(true);
        sendMessageTemplate.setChatId(chatId);

        if (messageId != 0) {
            sendMessageTemplate.setReplyToMessageId(messageId);
        }
        return sendMessageTemplate;
    }

    public String getRandomElementFromList(List<String> words) {
        return words.get(ThreadLocalRandom.current().nextInt(0, words.size()));
    }
}
