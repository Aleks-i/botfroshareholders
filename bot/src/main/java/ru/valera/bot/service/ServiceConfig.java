package ru.valera.bot.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.lang.NonNull;
import ru.valera.bot.model.chat.MailerType;
import ru.valera.bot.service.handler.Currency;
import ru.valera.bot.service.handler.Handler;
import ru.valera.bot.service.handler.mining.MiningCallBack;
import ru.valera.bot.service.mailer.MailerJob;
import ru.valera.bot.service.mailer.MailerTrigger;
import ru.valera.bot.to.MessageType;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.valera.bot.service.handler.media.MediaStorage.MEDIA_STORAGE_PATHS;

@Slf4j
@Configuration
public class ServiceConfig {

    public static final int PRIORITY_FOR_HANDLER = 4;

    @Autowired
    private Currency currencyHandler;
    @Autowired
    private MiningCallBack miningCallBackHandler;

    @Bean
    public Map<MessageType, Handler> getHandlerMap(@NonNull Collection<Handler> handlers) {
        return handlers.stream()
                .flatMap(handler -> handler.getMessageType().stream().
                        map(command -> new AbstractMap.SimpleEntry<>(
                                command,
                                handler)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Bean
    public Map<MailerType, MailerJob> getJobMap(@NonNull Collection<MailerJob> jobs) {
        return jobs.stream()
                .collect(Collectors.toMap(MailerJob::getSenderType, Function.identity()));
    }

    @Bean
    public Map<MailerType, MailerTrigger> getTriggerMap(@NonNull Collection<MailerTrigger> triggers) {
        return triggers.stream()
                .collect(Collectors.toMap(MailerTrigger::getSenderType, Function.identity()));
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return objectMapper;
    }

    @EventListener({ApplicationStartedEvent.class})
    public void init() {
        startThread(currencyHandler);
        //TODO вынести логику в метод startThread(Runnable handler) при запуске
        Thread miningThreadHandler = new Thread(miningCallBackHandler);
        miningThreadHandler.setDaemon(true);
        miningThreadHandler.setName("MiningHandler");
        miningThreadHandler.setPriority(PRIORITY_FOR_HANDLER);
//        miningThreadHandler.start();

        MEDIA_STORAGE_PATHS.forEach((k, v) -> {
            File sourceDir = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(k)).getFile());
            Arrays.stream(Objects.requireNonNull(sourceDir.listFiles())).forEach(fp -> v.add(fp.getPath()));
        });
    }

    private void startThread(Runnable handler) {
        Thread threadHandler = new Thread(handler);
        threadHandler.setDaemon(true);
        threadHandler.setName(handler.getClass().getSimpleName().toLowerCase());
        threadHandler.setPriority(PRIORITY_FOR_HANDLER);
        threadHandler.start();
    }
}
