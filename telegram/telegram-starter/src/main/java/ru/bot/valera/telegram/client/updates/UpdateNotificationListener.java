package ru.bot.valera.telegram.client.updates;

import ru.bot.valera.telegram.client.TdApi;

public interface UpdateNotificationListener<T extends TdApi.Update> {

    /**
     * Handles incoming update event.
     *
     * @param notification incoming update from TDLib
     */
    void handleNotification(T notification);

    /**
     * @return listener class type
     */
    Class<T> notificationType();

}
