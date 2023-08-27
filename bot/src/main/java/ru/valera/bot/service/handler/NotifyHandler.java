package ru.valera.bot.service.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.valera.bot.model.Content;
import ru.valera.bot.to.MessageType;
import ru.valera.bot.to.UpdateTO;

import java.util.Set;

import static ru.valera.bot.util.MessageUtil.setParamMessage;

@Component
public class NotifyHandler implements Handler {

    private final static int MILLISEC_IN_SEC = 1000;
    private final static String WRONG_INPUT_MESSAGE = "Братан, ты что то неверно вводишь. Должна быть команда вида: \"/timer\" и секунды через пробел";
    private final static String INPUT_MESSAGE_OK = "Братан, таймер запущен";

    @Override
    public Content handle(UpdateTO updateTO) {
        SendMessage sendMessage = new SendMessage();
        String delay = updateTO.getMessage().split(" ")[1];
        if ("".equals(delay)) {
            sendMessage.setText("You must specify the delay time. Like this:\n" +
                    "/notify 30");
            return new Content(sendMessage, updateTO.getMessageType());
        }
        long timeInSec;
        try {
            timeInSec = Long.parseLong(delay.trim());
        } catch (NumberFormatException e) {
            sendMessage.setText(WRONG_INPUT_MESSAGE);
            return new Content(sendMessage, updateTO.getMessageType());
        }
        if (timeInSec > 0) {
            Thread thread = new Thread(new Notify(timeInSec * MILLISEC_IN_SEC, updateTO.getChatId()));
            thread.start();
        } else {
            sendMessage.setText(WRONG_INPUT_MESSAGE);
            return new Content(sendMessage, updateTO.getMessageType());
        }
        sendMessage.setText(INPUT_MESSAGE_OK);
        return new Content(setParamMessage(sendMessage, updateTO), updateTO.getMessageType());
    }

    @Override
    public Set<MessageType> getMessageType() {
        return Set.of(MessageType.NOTIFY);
    }
}
