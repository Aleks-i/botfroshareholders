package ru.bot.valera.bot.bot;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.invoices.SendInvoice;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;

import java.io.File;
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
            while (true) {
                while (!sendQueue.isEmpty()) {
                    Content content = sendQueue.poll();
                    log.info("sendQueue receive new content command: {}", content.getCommand());
                    send(content);
                    try {
                        TimeUnit.MILLISECONDS.sleep(40);
                    } catch (InterruptedException e) {
                        log.error("Take interrupt while operate msg list", e);
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void send(Content content) {

        try {
            Command command = content.getCommand();
            switch (command) {
                case GREETING, VALERA, GIRLS, MINING, HELP, STICKER, NEW_USER, WEATHER, CURRENCY,
                        STATUSES, ANECDOTE, HOROSCOPE, START, END, DEFAULT, SETTINGS, LEFT_USER, TEXT_CONTAIN_EMOJI,
                        EXCEPTION_HANDLER, MANS, OTHER_VIDEO, FROM_ME_TEXT, MAIN_EVENTS_OF_DAY, CALLBACK_MINING -> {
                    SendMessage message = (SendMessage) content.getContent();
                    log.info("send message, command {}", command);
                    log.debug("Use Execute for " + content);
                    bot.execute(message);
                }
                case TITS_GIF, MANS_GIF, FROM_ME_ANIMATION -> {
                    SendAnimation animation = (SendAnimation) content.getContent();
                    log.info("send message, command {}", command);
                    log.debug("Use Execute for " + content);
                    bot.execute(animation);
                    deleteTempFile(animation.getAnimation().getNewMediaFile());
                }
                case TITS_VIDEO, GIRLS_VIDEO, GOOD_MORNING, GOOD_NIGHT, ROCK, FRIDAY, MANS_VIDEO, FROM_ME_VIDEO -> {
                    SendVideo video = (SendVideo) content.getContent();
                    log.debug("Use Execute for " + content);
                    bot.execute(video);
                    deleteTempFile(video.getVideo().getNewMediaFile());
                }
                case FROM_ME_PHOTO -> {
                    SendPhoto photo = (SendPhoto) content.getContent();
                    log.info("send message, command {}", command);
                    log.debug("Use Execute for " + content);
                    bot.execute(photo);
                    deleteTempFile(photo.getPhoto().getNewMediaFile());
                }
                case CALLBACK_SETTINGS, EDIT_WEATHER_KEYBOARD -> {
                    EditMessageReplyMarkup editMessageReplyMarkup = (EditMessageReplyMarkup) content.getContent();
                    log.info("send message, command {}", command);
                    log.debug("Use Execute for " + content);
                    bot.execute(editMessageReplyMarkup);
                }
                case PAYMENT -> {
                    SendInvoice sendInvoice = (SendInvoice) content.getContent();
                    log.info("send message, command {}", command);
                    log.debug("Use Execute for " + content);
                    bot.execute(sendInvoice);
                }
//                case STICKER -> {
//                    SendSticker sendSticker = (SendSticker) content.getContent();
//                    log.debug("Use SendSticker for " + content);
//                    bot.execute(sendSticker);
//                }
                case NONE -> {
                    log.info("send message, command {}", command);
                }
                default -> log.warn("Cant detect type of command {} in MessageSender", content.getCommand());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void deleteTempFile(File file) {
        if (file.getAbsolutePath().contains("tmp")) {
            if (!file.delete()) {
                log.warn("can't delete the temporary file {}", file);
            } else {
                log.info("file delete successful {}", file);
            }
        }
    }
}
