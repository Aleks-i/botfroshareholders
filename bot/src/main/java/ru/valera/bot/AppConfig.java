package ru.valera.bot;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.valera.bot.bot.Bot;
import ru.valera.bot.bot.MessageReceiver;
import ru.valera.bot.bot.MessageSender;
import ru.valera.bot.model.chat.Chat;
import ru.valera.bot.repository.ChatRepository;
import ru.valera.bot.to.ChatTo;
import ru.valera.bot.util.JsonUtil;

import java.util.List;

@Slf4j
@Component
public class AppConfig {
    private static final int RECONNECT_PAUSE = 5_000;
    private static final int PRIORITY_FOR_RECEIVER = 3;
    private static final int PRIORITY_FOR_SENDER = 2;

    @Autowired
    private MessageReceiver messageReceiver;
    @Autowired
    private MessageSender messageSender;
    @Autowired
    private Bot bot;
    @Autowired
    private ChatRepository chatRepository;

    @EventListener({ApplicationReadyEvent.class})
    public void init() {

        Thread receiver = new Thread(messageReceiver);
        receiver.setDaemon(true);
        receiver.setName("MsgReciever");
        receiver.setPriority(PRIORITY_FOR_RECEIVER);
        receiver.start();

        Thread sender = new Thread(messageSender);
        sender.setDaemon(true);
        sender.setName("MsgSender");
        sender.setPriority(PRIORITY_FOR_SENDER);
        sender.start();

        botConnect();

        List<Chat> chats = chatRepository.findAll();
        chats.forEach(ch -> {
            Bot.CHAT_STORAGE.put(ch.getChatId(), new ChatTo(ch));
        });
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
