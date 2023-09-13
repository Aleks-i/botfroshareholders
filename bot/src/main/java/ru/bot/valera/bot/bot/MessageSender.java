package ru.bot.valera.bot.bot;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.invoices.SendInvoice;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import ru.bot.valera.bot.model.Content;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageSender implements Runnable {
    public static final Queue<Content> sendQueue = new ConcurrentLinkedQueue<>();

    private final Bot bot;

    @PostConstruct
    private void initMessageSender() {

    }

    @Override
    public void run() {
        try {
            Content content;
            while (true) {
                while (!sendQueue.isEmpty()) {
                    content = sendQueue.poll();
                    log.debug("Get new msg to send " + content);
                    log.info("send content {}", content);
                    send(content);
                }
//                for (Content content = sendQueue.poll(); content != null; content = sendQueue.poll()) {
//                    log.debug("Get new msg to send " + content);
//                    send(content);
//                }
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    log.error("Take interrupt while operate msg list", e);
                }
            }
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }
    }

    private void send(Content content) {

        try {
            switch (content.getCommand()) {
                case GREETING, VALERA, GIRLS, MINING, HELP, STICKER, NEW_USER, WEATHER, CALLBACK_WEATHER, CURRENCY,
                        STATUSES, ANECDOTE, HOROSCOPE, START, END, DEFAULT, SETTINGS, LEFT_USER, TEXT_CONTAIN_EMOJI,
                        EXCEPTION_HANDLER, MANS -> {
                    SendMessage message = (SendMessage) content.getContent();
                    log.debug("Use Execute for " + content);
                    bot.execute(message);
                }
                case TITS_GIF, MANS_GIF -> {
                    SendAnimation animate = (SendAnimation) content.getContent();
                    log.debug("Use Execute for " + content);
                    bot.execute(animate);
                }
                case TITS_VIDEO, GIRLS_VIDEO, GOOD_MORNING, GOOD_NIGHT, ROCK, FRIDAY, MANS_VIDEO -> {
                    SendVideo video = (SendVideo) content.getContent();
                    log.debug("Use Execute for " + content);
                    bot.execute(video);
                }
                case CALLBACK_SETTINGS, EDIT_WEATHER_KEYBOARD -> {
                    EditMessageReplyMarkup editMessageReplyMarkup = (EditMessageReplyMarkup) content.getContent();
                    log.debug("Use Execute for " + content);
                    bot.execute(editMessageReplyMarkup);
                }
                case PAYMENT -> {
                    SendInvoice sendInvoice = (SendInvoice) content.getContent();
                    log.debug("Use Execute for " + content);
                    bot.execute(sendInvoice);
                }
//                case STICKER -> {
//                    SendSticker sendSticker = (SendSticker) content.getContent();
//                    log.debug("Use SendSticker for " + content);
//                    bot.execute(sendSticker);
//                }
                case NONE -> {
                }
                default -> log.warn("Cant detect type of content {} in MessageSender", content);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
