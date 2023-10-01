package ru.bot.valera.bot;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.bot.valera.bot.bot.Bot;
import ru.bot.valera.bot.bot.MessageReceiver;
import ru.bot.valera.bot.bot.MessageSender;
import ru.bot.valera.bot.model.persist.chat.Chat;
import ru.bot.valera.bot.repository.ChatRepository;
import ru.bot.valera.bot.util.JsonUtil;

import java.util.List;

import static ru.bot.valera.bot.bot.Bot.CHAT_STORAGE;
import static ru.bot.valera.bot.util.Util.initContentCounter;

@Slf4j
@Getter
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppConfig {

    private static final int RECONNECT_PAUSE = 5_000;
    private static final int PRIORITY_FOR_RECEIVER = 3;
    private static final int PRIORITY_FOR_SENDER = 2;

    final Bot bot;
    final MessageSender messageSender;
    final MessageReceiver messageReceiver;
    final ChatRepository chatRepository;

    @EventListener({ApplicationReadyEvent.class})
    public void init() {
        startThread(messageReceiver, "MsgReceiver", PRIORITY_FOR_RECEIVER);
        startThread(messageSender, "MsgSender", PRIORITY_FOR_SENDER);

        List<Chat> chats = chatRepository.findAll();
        chats.forEach(ch -> {
            CHAT_STORAGE.put(ch.getChatId(), ch);
        });

        CHAT_STORAGE.forEach((k, e) -> {
            initContentCounter(k);
        });

        botConnect();
    }

    private void startThread(Runnable runnable, String threadName, int priority) {
        Thread receiver = new Thread(runnable);
        receiver.setDaemon(true);
        receiver.setName(threadName);
        receiver.setPriority(priority);
        receiver.start();
    }

    private void botConnect() {
        try {
            TelegramBotsApi telegram = new TelegramBotsApi(DefaultBotSession.class);
            telegram.registerBot(bot);
        } catch (TelegramApiException e) {
            log.error("Cant Connect. Pause " + RECONNECT_PAUSE + "sec and try again. Error: " + e.getMessage());
            try {
                Thread.sleep(RECONNECT_PAUSE);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
                return;
            }
            botConnect();
        }
    }

    @Autowired
    public void storeObjectMapper(ObjectMapper objectMapper) {
        JsonUtil.setMapper(objectMapper);
    }
}
