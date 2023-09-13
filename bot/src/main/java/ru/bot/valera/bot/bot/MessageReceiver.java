package ru.bot.valera.bot.bot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.service.Service;
import ru.bot.valera.bot.to.UpdateTO;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageReceiver implements Runnable {
    public static final Queue<UpdateTO> receiveQueue = new ConcurrentLinkedQueue<>();

    private static final int WAIT_FOR_NEW_MESSAGE_DELAY = 100;

    private final Service service;

    @Override
    public void run() {
        while (true) {
            for (UpdateTO updateTo = receiveQueue.poll(); updateTo != null; updateTo = receiveQueue.poll()) {
                log.debug("Update recieved in MessageReceiver" );
                service.process(updateTo);
            }
            try {
                TimeUnit.MILLISECONDS.sleep(WAIT_FOR_NEW_MESSAGE_DELAY);
            } catch (InterruptedException e) {
                log.error("Catch interrupt. Exit", e);
                return;
            }
        }
    }
}
