package ru.valera.bot.service;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.valera.bot.client.TdApi;
import ru.valera.bot.client.TelegramClient;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;

import static ru.valera.bot.service.MessageType.ME;
import static util.MessageUtil.HOLIDAYS_WORDS;
import static util.MessageUtil.HOROSCOPE_WORDS;

@Slf4j
@Getter
@Service
public class TelegramClientService {

    private final TelegramClient telegramClient;

    private final Deque<TdApi.Message> messages = new ConcurrentLinkedDeque<>();

    private final Map<MessageType, String> messageTarget = new HashMap<>();
    private static final String POLITE_AND_EVIL = "Vежливые и Zлые";
    private static final String HOROSCOPE = "Нейрогороскопы";

    public TelegramClientService(@Lazy TelegramClient telegramClient) {
        this.telegramClient = telegramClient;
    }

    public void putMessage(TdApi.Message msg) {
        messages.addLast(msg);
    }

    @PostConstruct
    public void init() {
        messageTarget.put(MessageType.HOLIDAYS, "");
        messageTarget.put(MessageType.HOROSCOPE, "");
        messageTarget.put(ME, "");
    }

    @Scheduled(fixedDelay = 1000)
    private void handleMessages() {
        TdApi.Message message = messages.pollFirst();
        if (message != null) {

            TdApi.MessageContent content = message.content;
            if (content instanceof TdApi.MessagePhoto mt) {
                TdApi.Chat chat = telegramClient.sendSync(new TdApi.GetChat(message.chatId), TdApi.Chat.class);
//                log.info("Incoming text message:\n[\n\ttitle: {},\n\tchatId: {}\n]", chat.title, mt.text.text);
                log.info("Incoming text message:\n[\n\ttitle: {},\n\tchatId: {},\n\tmessage to photo: {}\n]", chat.title, chat.id, mt.caption.text);
                checkTitle(chat.title, mt.caption.text);
            }
            if (content instanceof TdApi.MessageText mt) {
                TdApi.Chat chat = telegramClient.sendSync(new TdApi.GetChat(message.chatId), TdApi.Chat.class);
//                log.info("Incoming text message:\n[\n\ttitle: {},\n\tchatId: {}\n]", chat.title, mt.text.text);
                log.info("Incoming text message:\n[\n\ttitle: {},\n\tchatId: {},\n\tmessage: {}\n]", chat.title, chat.id, mt.text.text);
                checkTitle(chat.title, mt.text.text);
            }
        }
    }

    private void checkTitle(String title, String message) {
        switch (title) {
            case POLITE_AND_EVIL -> {
                if (checkMessage(HOLIDAYS_WORDS, message)) {
                    messageTarget.put(MessageType.HOLIDAYS, message);
                }
            }
            case HOROSCOPE -> {
                if (checkMessage(HOROSCOPE_WORDS, message)) {
                    messageTarget.put(MessageType.HOROSCOPE, message);
                }
            }
//            case ME -> {
//                if (checkMessage(HOROSCOPE_WORDS, message)) {
//                    messageTarget.put(MessageType.HOROSCOPE, message);
//                }
//            }
        }
    }

    private boolean checkMessage(Set<String> requiredWords, String message) {
        Set<String> messageWords = getWordsSet(message);
        for (String s : requiredWords) {
            if (!messageWords.contains(s)) {
                return false;
            }
        }
        return true;
    }

    private Set<String> getWordsSet(String message) {
        return Set.of(message.split(" "));
    }
}
