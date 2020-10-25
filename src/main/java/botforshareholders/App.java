package botforshareholders;

import botforshareholders.bot.Bot;
import botforshareholders.bot.MessageReciever;
import botforshareholders.bot.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.telegram.telegrambots.ApiContextInitializer;

import static botforshareholders.Security.botToken;
import static botforshareholders.Security.botUserName;

public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);
    private static final int PRIORITY_FOR_SENDER = 1;
    private static final int PRIORITY_FOR_RECEIVER = 3;
    private static MessageReciever messageReciever;
    private static MessageSender messageSender;

    public static void main(String[] args) {
        log.info("bot app main start");
        ApiContextInitializer.init();

        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml",
                "spring/spring-db.xml")) {
            messageReciever = appCtx.getBean(MessageReciever.class);
            messageSender = appCtx.getBean(MessageSender.class);
        }

        Bot botForShareholders = new Bot(botUserName, botToken);

        messageReciever.setBot(botForShareholders);
        messageSender.setBot(botForShareholders);

        botForShareholders.botConnect();

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
    }
}
