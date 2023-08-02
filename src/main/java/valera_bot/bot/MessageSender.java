package valera_bot.bot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.Message;

import static valera_bot.bot.Bot.sendQueue;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageSender implements Runnable {
    private static final int SENDER_SLEEP_TIME = 100;

    @Autowired
    private Bot bot;

    @Override
    public void run() {
        log.info("[STARTED] MsgSender.  Bot class: " + bot);
        try {
            while (true) {
                for (Object object = sendQueue.poll(); object != null; object = sendQueue.poll()) {
                    log.debug("Get new msg to send " + object);
                    send(object);
                }
                try {
                    Thread.sleep(SENDER_SLEEP_TIME);
                } catch (InterruptedException e) {
                    log.error("Take interrupt while operate msg list", e);
                }
            }
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }
    }

    private void send(Object object) {
        try {
            MessageType messageType = messageType(object);
            switch (messageType) {
                case EXECUTE:
                    BotApiMethod<Message> message = (BotApiMethod<Message>) object;
                    log.debug("Use Execute for " + object);
                    bot.execute(message);
                    break;
                /*case STICKER:
                    SendSticker sendSticker = (SendSticker) object;
                    LOG.debug("Use SendSticker for " + object);
                    bot.sendSticker(sendSticker);
                    break;*/
                default:
                    log.warn("Cant detect type of object. " + object);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private MessageType messageType(Object object) {
        if (object instanceof SendSticker) return MessageType.STICKER;
        if (object instanceof BotApiMethod) return MessageType.EXECUTE;
        return MessageType.NOT_DETECTED;
    }

    enum MessageType {
        EXECUTE, STICKER, NOT_DETECTED,
    }
}
