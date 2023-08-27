package ru.valera.bot.service.handler.mining;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.valera.bot.model.Content;
import ru.valera.bot.service.handler.Handler;
import ru.valera.bot.service.keyboard.CallBackMiningKeyboard;
import ru.valera.bot.to.MessageType;
import ru.valera.bot.to.UpdateTO;
import ru.valera.bot.util.MessageUtil;

import java.util.Set;

//minerstate
@Slf4j
@Component
@RequiredArgsConstructor
public class Mining implements Handler {

    @Autowired
    private final CallBackMiningKeyboard callBackMiningKeyboard;

    @Override
    public Content handle(UpdateTO updateTO) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setReplyMarkup(callBackMiningKeyboard.getInlineKeyboardMarkupWeather());
        sendMessage.setText("Пока в наличии только зеленые: ");
        return new Content(MessageUtil.setParamMessage(sendMessage, updateTO), updateTO.getMessageType());
    }


    @Override
    public Set<MessageType> getMessageType() {
        return Set.of(MessageType.MINING);
    }
}
