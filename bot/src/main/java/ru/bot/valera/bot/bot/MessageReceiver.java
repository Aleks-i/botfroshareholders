package ru.bot.valera.bot.bot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.service.Service;
import ru.bot.valera.bot.to.UpdateTo;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageReceiver implements Runnable {
    public static final Queue<UpdateTo> receiveQueue = new ConcurrentLinkedQueue<>();

    private static final int WAIT_FOR_NEW_MESSAGE_DELAY = 100;

    private final Service service;

    @Override
    public void run() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        while (true) {
            while (!receiveQueue.isEmpty()) {
                UpdateTo updateTo = receiveQueue.poll();
                executorService.submit(() -> {
                    log.info("receiveQueue receive new update, command: {}", updateTo.getCommand());
                    service.process(updateTo);
                });
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
