package ru.bot.valera.bot.bot;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.persist.chat.Chat;
import ru.bot.valera.bot.service.ParserCommand;
import ru.bot.valera.bot.service.handlers.exception.ContentShowCounter;
import ru.bot.valera.bot.to.UpdateTo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static ru.bot.valera.bot.bot.MessageReceiver.receiveQueue;

@Slf4j
@Getter
@Setter
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Bot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    String botUsername;
    @Value("${bot.token}")
    String botToken;
    public static final int COUNT_CONTENT_IN_DAY = 1;
    final ParserCommand parserCommand;

    public static final Map<Long, Chat> CHAT_STORAGE = new ConcurrentHashMap<>();
    public static final Map<Long, Map<Command, ContentShowCounter>> CHATS_CONTENT_COUNTER = new ConcurrentHashMap<>();

    @Override
    public void onUpdateReceived(Update update) {
        UpdateTo parsedUpdate = parserCommand.getParsedUpdate(update);
        log.info("bot get update command {}", parsedUpdate.getCommand());
        receiveQueue.add(parsedUpdate);
    }

    @Override
    public String toString() {
        return "Bot{" +
                "botUsername='" + botUsername + '\'' +
                '}';
    }
}
