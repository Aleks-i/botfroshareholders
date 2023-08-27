package ru.valera.bot.bot;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.valera.bot.to.ChatTo;
import ru.valera.bot.util.MessengerFileWriter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static ru.valera.bot.bot.MessageReceiver.receiveQueue;

@Component
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
public class Bot extends TelegramLongPollingBot {

    public static final Map<Long, ChatTo> CHAT_STORAGE = new ConcurrentHashMap<>();

    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    @Override
    public void onUpdateReceived(Update update) {
        MessengerFileWriter.writeMessageToFile(update.getMessage());
        receiveQueue.add(update);
    }

    @Override
    public String toString() {
        return "Bot{" +
                "botUsername='" + botUsername + '\'' +
                '}';
    }
}
