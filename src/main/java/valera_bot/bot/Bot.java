package valera_bot.bot;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static valera_bot.MessengerFileWriter.writeMessageToFile;

@Component
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
public class Bot extends TelegramLongPollingBot {

    public static final Queue<Object> sendQueue = new ConcurrentLinkedQueue<>();
    public static final Queue<Object> receiveQueue = new ConcurrentLinkedQueue<>();

    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    @Override
    public void onUpdateReceived(Update update) {
        log.info("Receive new Update. updateID: {}", update.toString());
        writeMessageToFile(update.getMessage());
        receiveQueue.add(update);
    }

    @Override
    public String toString() {
        return "Bot{" +
                "botUsername='" + botUsername + '\'' +
                '}';
    }
}
