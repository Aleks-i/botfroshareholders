package ru.valera.bot.service.handler.media;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.valera.bot.model.Content;
import ru.valera.bot.service.handler.Handler;
import ru.valera.bot.service.keyboard.GirlsKeyboard;
import ru.valera.bot.to.MessageType;
import ru.valera.bot.to.UpdateTO;
import ru.valera.bot.util.MessageUtil;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class Media implements Handler {

    @Autowired
    private final GirlsKeyboard girlsKeyboard;

    @Override
    public Content handle(UpdateTO updateTO) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setReplyMarkup(girlsKeyboard.getInlineKeyboardMarkupWeather());
        sendMessage.setText("Что выберешь: ");
        return new Content(MessageUtil.setParamMessage(sendMessage, updateTO), updateTO.getMessageType());
    }

    @Override
    public Set<MessageType> getMessageType() {
        return Set.of(MessageType.GIRLS);
    }
}
