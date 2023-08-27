package ru.valera.bot.bot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.valera.bot.service.Service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageReceiver implements Runnable {
    private static final int WAIT_FOR_NEW_MESSAGE_DELAY = 100;
    public static final Queue<Update> receiveQueue = new ConcurrentLinkedQueue<>();

    private final Service service;

    @Override
    public void run() {
        while (true) {
            for (Update update = receiveQueue.poll(); update != null; update = receiveQueue.poll()) {
                log.info("Update recieved in MessageReceiver");
                service.operate(update);
            }
            try {
                Thread.sleep(WAIT_FOR_NEW_MESSAGE_DELAY);
            } catch (InterruptedException e) {
                log.error("Catch interrupt. Exit", e);
                return;
            }
        }
    }
}
