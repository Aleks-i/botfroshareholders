package ru.bot.valera.telegram.service;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.bot.valera.telegram.client.TdApi;
import ru.bot.valera.telegram.client.TelegramClient;
import ru.bot.valera.telegram.util.MessageUtil;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;

@Slf4j
@Getter
@Service
public class TelegramClientService {

    private final TelegramClient telegramClient;

    private final Deque<TdApi.Message> messages = new ConcurrentLinkedDeque<>();

    private final Map<MessageType, String> messageTarget = new HashMap<>();

    private static final long POLITE_AND_EVIL_ID = -1001793975669L;
    private static final long HOROSCOPE_ID = -1001495354288L;

    public TelegramClientService(@Lazy TelegramClient telegramClient) {
        this.telegramClient = telegramClient;
    }

    public void putMessage(TdApi.Message msg) {
        messages.addLast(msg);
    }

    @PostConstruct
    public void init() {
        messageTarget.put(MessageType.HOLIDAYS, "" );
        messageTarget.put(MessageType.HOROSCOPE, "" );
    }

    @Scheduled(fixedDelay = 1000)
    private void handleMessages() {
        TdApi.Message message = messages.pollFirst();
        if (message != null) {

            TdApi.MessageContent content = message.content;

            if (content instanceof TdApi.MessagePhoto mt) {
                TdApi.Chat chat = telegramClient.sendSync(new TdApi.GetChat(message.chatId), TdApi.Chat.class);
                log.info("Incoming text message:\n[\n\ttitle: {},\n\tchatId: {},\n\tmessage to photo: {}\n]", chat.title, chat.id, mt.caption.text);
                fillMessageTarget(chat.id, mt.caption.text);
            }
            if (content instanceof TdApi.MessageText mt) {
                TdApi.Chat chat = telegramClient.sendSync(new TdApi.GetChat(message.chatId), TdApi.Chat.class);
                log.info("Incoming text message:\n[\n\ttitle: {},\n\tchatId: {},\n\tmessage: {}\n]", chat.title, chat.id, mt.text.text);
                fillMessageTarget(chat.id, mt.text.text);
            }
        }
    }

    private void fillMessageTarget(Long chatId, String message) {
        if (chatId == POLITE_AND_EVIL_ID && checkMatchesInMessage(MessageUtil.HOLIDAYS_WORDS, message)) {
            messageTarget.put(MessageType.HOLIDAYS, message);
        } else if (chatId == HOROSCOPE_ID && checkMatchesInMessage(MessageUtil.HOROSCOPE_WORDS, message)) {
            messageTarget.put(MessageType.HOROSCOPE, message);
        }
    }

    private boolean checkMatchesInMessage(Set<String> requiredWords, String message) {
        for (String s : requiredWords) {
            if (!message.contains(s)) {
                return false;
            }
        }
        return true;
    }
}
