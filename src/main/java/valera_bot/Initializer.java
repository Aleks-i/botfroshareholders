package valera_bot;

import valera_bot.bot.Bot;
import valera_bot.bot.MessageReciever;
import valera_bot.bot.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Component
public class Initializer {
    final static int RECONNECT_PAUSE = 10_000;
    private static final int PRIORITY_FOR_RECEIVER = 3;
    private static final int PRIORITY_FOR_SENDER = 1;

    @Autowired
    private MessageReciever messageReciever;
    @Autowired
    private MessageSender messageSender;
    @Autowired
    private Bot bot;

    @EventListener({ContextRefreshedEvent.class})
    public void init() {

        Thread receiver = new Thread(messageReciever);
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
    }

    private void botConnect() {
        try {
            TelegramBotsApi telegram = new TelegramBotsApi(DefaultBotSession.class);
            telegram.registerBot(bot);
            log.info("[STARTED] TelegramAPI. Bot Connected. Bot: " + bot);
        } catch (TelegramApiException e) {
            log.error("Cant Connect. Pause " + RECONNECT_PAUSE / 1000 + "sec and try again. Error: " + e.getMessage());
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
