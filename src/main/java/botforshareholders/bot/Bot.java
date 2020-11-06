package botforshareholders.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static botforshareholders.MessengerFileWriter.writeMessageToFile;

@Component
public class Bot extends TelegramLongPollingBot {
    private static final Logger LOG = LoggerFactory.getLogger(Bot.class);
    final int RECONNECT_PAUSE = 10000;

    public static final Queue<Object> sendQueue = new ConcurrentLinkedQueue<>();
    public static final Queue<Object> receiveQueue = new ConcurrentLinkedQueue<>();

    private String botUsername;
    private String botToken;

    public Bot(@Qualifier("beanBotUsername") String botUsername, @Qualifier("beanBotToken") String botToken) {
        this.botUsername = botUsername;
        this.botToken = botToken;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    public void setBotUsername(String botUsername) {
        this.botUsername = botUsername;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        LOG.debug("Receive new Update. updateID: {}", update.getUpdateId());
        writeMessageToFile(update.getMessage());
        receiveQueue.add(update);
    }

    public void botConnect() {
        ApiContextInitializer.init();
        TelegramBotsApi telegram = new TelegramBotsApi();
        try {
            telegram.registerBot(this);
            LOG.info("[STARTED] TelegramAPI. Bot Connected. Bot class: " + this);
        } catch (TelegramApiRequestException e) {
            LOG.error("Cant Connect. Pause " + RECONNECT_PAUSE / 1000 + "sec and try again. Error: " + e.getMessage());
            try {
                Thread.sleep(RECONNECT_PAUSE);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
                return;
            }
            botConnect();
        }
    }
}
