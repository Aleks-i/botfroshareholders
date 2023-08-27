package ru.valera.bot.client;

import ru.valera.bot.client.updates.UpdateNotificationListener;

import java.util.function.Consumer;

class UpdateNotificationConsumer<T extends TdApi.Update> implements Consumer<TdApi.Object> {

    private final UpdateNotificationListener<T> notificationListener;

    private final Class<T> type;

    public UpdateNotificationConsumer(UpdateNotificationListener<T> notificationListener, Class<T> clazz) {
        this.notificationListener = notificationListener;
        this.type = clazz;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(TdApi.Object object) {
        T notification = type.cast(object);
        notificationListener.handleNotification(notification);
    }

}
