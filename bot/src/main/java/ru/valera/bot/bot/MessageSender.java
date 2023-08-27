package ru.valera.bot.bot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import ru.valera.bot.model.Content;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageSender implements Runnable {
    public static final Queue<Content> sendQueue = new ConcurrentLinkedQueue<>();

    @Autowired
    private Bot bot;

    @Override
    public void run() {
        try {
            while (true) {
                for (Content content = sendQueue.poll(); content != null; content = sendQueue.poll()) {
                    log.debug("Get new msg to send " + content);
                    send(content);
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(1);
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
            switch (content.getMessageType()) {
                case GREETING, VALERA, GIRLS, MINING, STICKER, HELP, NEW_USER, WEATHER, CALLBACK_WEATHER, CURRENCY,
                        NOTIFY, STATUSES_VK, ANECDOTE, START, END, CUSTOM -> {
                    SendMessage message = (SendMessage) content.getContent();
                    log.debug("Use Execute for " + content);
                    bot.execute(message);
                }
                case TITS_GIF -> {
                    SendAnimation animate = (SendAnimation) content.getContent();
                    log.debug("Use Execute for " + content);
                    bot.execute(animate);
                }
                case TITS_VIDEO, GIRLS_VIDEO -> {
                    SendVideo video = (SendVideo) content.getContent();
                    log.debug("Use Execute for " + content);
                    bot.execute(video);
                }
                /*case STICKER:
                    SendSticker sendSticker = (SendSticker) content;
                    LOG.debug("Use SendSticker for " + content);
                    bot.sendSticker(sendSticker);
                    break;*/
                case DEFAULT -> {
                }
                default -> log.warn("Cant detect type of content {} in MessegeSender", content);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
