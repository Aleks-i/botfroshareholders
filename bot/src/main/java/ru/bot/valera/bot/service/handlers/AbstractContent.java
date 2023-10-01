package ru.bot.valera.bot.service.handlers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.service.keyboards.Keyboard;
import ru.bot.valera.bot.to.UpdateTo;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class AbstractContent implements Handler {

    protected Content getContent(String text, UpdateTo updateTO, Keyboard keyboard) {
        return new Content(setParamMessage(getSendMessage(text, keyboard, updateTO.getChatId()), updateTO.getChatId(), updateTO.getMessageId()),
                updateTO.getCommand());
    }

    protected Content getContent(UpdateTo updateTO, Keyboard keyboard) {
        return new Content(getEditMessageReplyMarkup(keyboard, updateTO), updateTO.getCommand());
    }

    protected Content getContent(String text, UpdateTo updateTO) {
        return new Content(setParamMessage(getSendMessage(text), updateTO.getChatId(), updateTO.getMessageId()),
                updateTO.getCommand());
    }

    protected SendMessage getSendMessage(String text, Keyboard keyboard, long chatId) {
        SendMessage sendMessage = getSendMessage(text);
        sendMessage.setReplyMarkup(keyboard.getKeyboard(chatId));
        return sendMessage;
    }

    protected SendMessage getSendMessage(String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.enableMarkdown(true);
        return sendMessage;
    }

    protected EditMessageReplyMarkup getEditMessageReplyMarkup(Keyboard keyboard, UpdateTo updateTO) {
        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();

        long chatId = updateTO.getChatId();
        editMessageReplyMarkup.setChatId(chatId);
        editMessageReplyMarkup.setMessageId(updateTO.getMessageId());
        editMessageReplyMarkup.setReplyMarkup((InlineKeyboardMarkup) keyboard.getKeyboard(chatId));
        return editMessageReplyMarkup;
    }

    protected SendMessage setParamMessage(SendMessage sendMessageTemplate, long chatId, int messageId) {
        sendMessageTemplate.enableMarkdown(true);
        sendMessageTemplate.setChatId(chatId);

        if (messageId != 0) {
            sendMessageTemplate.setReplyToMessageId(messageId);
        }
        return sendMessageTemplate;
    }

    protected String getRandomElementFromList(List<String> words) {
        return words.get(ThreadLocalRandom.current().nextInt(0, words.size()));
    }

    protected void formatAndSetMarkdownV2ForSendVideo(SendVideo video) {
        video.setParseMode("MarkdownV2");
        video.setCaption(video.getCaption().replaceAll("\\.", "\\\\."));
        video.setCaption(video.getCaption().replaceAll("!", "\\\\!"));
        video.setCaption(video.getCaption().replaceAll("\\(", "\\\\("));
        video.setCaption(video.getCaption().replaceAll("\\)", "\\\\)"));
        video.setCaption(video.getCaption().replaceAll("-", "\\\\-"));
    }
}
