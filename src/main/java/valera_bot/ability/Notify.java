package valera_bot.ability;

import valera_bot.bot.Bot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;

public class Notify implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(Notify.class);
    private static final int MILLISEC_IN_SEC = 1000;

    Bot bot;
    long delayInMillisec;
    String chatID;

    public Notify(Bot bot, long delayInMillisec, String chatID) {
        this.bot = bot;
        this.delayInMillisec = delayInMillisec;
        this.chatID = chatID;
        LOG.debug("CREATE. {}", toString());
    }

    @Override
    public void run() {
        LOG.info("RUN. " + toString());
        bot.sendQueue.add(getFirstMessage());
        try {
            Thread.sleep(delayInMillisec);
            bot.sendQueue.add(getSecondSticker());
        } catch (InterruptedException e) {
            LOG.error(e.getMessage(), e);
        }
        LOG.info("FIHISH. " + toString());
    }

    private SendMessage getFirstMessage() {
        return new SendMessage(chatID, "I will send you notify after " + delayInMillisec / MILLISEC_IN_SEC + "sec");
    }

    private SendSticker getSecondSticker() {
        SendSticker sendSticker = new SendSticker();
//        sendSticker.setSticker("CAADBQADiQMAAukKyAPZH7wCI2BwFxYE");
        sendSticker.setChatId(chatID);
        return sendSticker;
    }

    private SendMessage getSecondMessage() {
        return new SendMessage(chatID, "This is notify message. Thanks for using :)");
    }
}
