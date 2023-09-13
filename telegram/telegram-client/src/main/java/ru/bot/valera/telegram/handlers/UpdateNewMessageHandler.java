package ru.bot.valera.telegram.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bot.valera.telegram.client.TdApi;
import ru.bot.valera.telegram.client.updates.UpdateNotificationListener;
import ru.bot.valera.telegram.service.TelegramClientService;

@Component
@Slf4j
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
