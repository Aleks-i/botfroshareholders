package ru.valera.bot.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.valera.bot.client.TdApi;
import ru.valera.bot.client.updates.UpdateNotificationListener;
import ru.valera.bot.service.TelegramClientService;

@Component @Slf4j
public class UpdateNewMessageHandler implements UpdateNotificationListener<TdApi.UpdateNewMessage> {

    private final TelegramClientService telegramService;

    public UpdateNewMessageHandler(TelegramClientService telegramService) {
        this.telegramService = telegramService;
    }

    @Override
    public void handleNotification(TdApi.UpdateNewMessage notification) {
        telegramService.putMessage(notification.message);
    }

    @Override
    public Class<TdApi.UpdateNewMessage> notificationType() {
        return TdApi.UpdateNewMessage.class;
    }

}
