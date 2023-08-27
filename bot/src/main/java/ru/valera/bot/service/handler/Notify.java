package ru.valera.bot.service.handler;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import ru.valera.bot.model.Content;
import ru.valera.bot.to.MessageType;

import static ru.valera.bot.bot.MessageSender.sendQueue;

@Slf4j
public class Notify implements Runnable {
    private static final int MILLISEC_IN_SEC = 1000;

    long delayInMillisecond;
    String chatID;

    public Notify(long delayInMillisecond, long chatID) {
        this.delayInMillisecond = delayInMillisecond;
        this.chatID = String.valueOf(chatID);
        log.debug("CREATE. {}", toString());
    }

    @Override
    public void run() {
        log.info("RUN. " + toString());
        sendQueue.add(getFirstMessage());
        try {
            Thread.sleep(delayInMillisecond);
            sendQueue.add(getSecondSticker());
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        log.info("FIHISH. " + toString());
    }

    private Content getFirstMessage() {
        return new Content(new SendMessage(chatID, "I will send you notify after " + delayInMillisecond / MILLISEC_IN_SEC + "sec"),
                MessageType.NOTIFY);
    }

    private Content getSecondSticker() {
        SendSticker sendSticker = new SendSticker();
//        sendSticker.setSticker("CAADBQADiQMAAukKyAPZH7wCI2BwFxYE");
        sendSticker.setChatId(chatID);
        return new Content(sendSticker, MessageType.NOTIFY);
    }

    private SendMessage getSecondMessage() {
        return new SendMessage(chatID, "This is notify message. Thanks for using :)");
    }
}
