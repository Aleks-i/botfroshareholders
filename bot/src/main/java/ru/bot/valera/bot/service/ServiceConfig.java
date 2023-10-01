package ru.bot.valera.bot.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.lang.NonNull;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.weather.Root;
import ru.bot.valera.bot.service.handlers.Currency;
import ru.bot.valera.bot.service.handlers.Handler;
import ru.bot.valera.bot.service.handlers.media.SourcePaths;
import ru.bot.valera.bot.service.handlers.mining.MiningCallBack;
import ru.bot.valera.bot.service.handlers.weather.WeatherCallback;
import ru.bot.valera.bot.service.schedulers.tasks.MailerJob;
import ru.bot.valera.bot.service.schedulers.tasks.MailerTrigger;
import ru.bot.valera.bot.util.JsonUtil;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.bot.valera.bot.service.handlers.media.AbstractMediaContent.MEDIA_STORAGE_PATHS;
import static ru.bot.valera.bot.service.handlers.media.AbstractMediaContent.ROOT_PATH;
import static ru.bot.valera.bot.util.WeatherUtil.CITIES_NAMES;
import static ru.bot.valera.bot.util.WeatherUtil.coordsList;

@Slf4j
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceConfig {

    public static final int PRIORITY_FOR_HANDLER = 4;

    final Currency currencyHandler;
    final MiningCallBack miningCallBackHandler;
    final WeatherCallback weatherCallback;

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return objectMapper;
    }

    @Bean
    public Map<Command, Handler> getHandlerMap(@NonNull Collection<Handler> handlers) {
        return handlers.stream()
                .flatMap(handler -> handler.getMessageType().stream()
                        .map(command -> new AbstractMap.SimpleEntry<>(
                                command, handler)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Bean
    public Map<Command, MailerJob> getJobMap(@NonNull Collection<MailerJob> jobs) {
        return jobs.stream()
                .collect(Collectors.toMap(MailerJob::getCommandType, Function.identity()));
    }

    @Bean
    public Map<Command, MailerTrigger> getTriggerMap(@NonNull Collection<MailerTrigger> triggers) {
        return triggers.stream()
                .collect(Collectors.toMap(MailerTrigger::getSchedulerType, Function.identity()));
    }

    @EventListener({ApplicationStartedEvent.class})
    public void initStartedEvent() {
        startThread(currencyHandler);
        startThread(miningCallBackHandler);

        initMediaStoragePaths();
        initWeatherCities();
    }

    private void initMediaStoragePaths() {
        Arrays.stream(SourcePaths.values())
                .forEach(s -> MEDIA_STORAGE_PATHS.put(ROOT_PATH + s.getSourcePath(), new ArrayList<>()));

        MEDIA_STORAGE_PATHS.forEach((sourcePath, storage) -> {
            File sourceDir = new File(sourcePath);
            Arrays.stream(Objects.requireNonNull(sourceDir.listFiles()))
                    .forEach(fp -> storage.add(fp.getPath()));
        });
    }

    private void startThread(Runnable handler) {
        Thread threadHandler = new Thread(handler);
        threadHandler.setDaemon(true);
        threadHandler.setName(handler.getClass().getSimpleName().toLowerCase());
        threadHandler.setPriority(PRIORITY_FOR_HANDLER);
        threadHandler.start();
    }

    private void initWeatherCities() {
        String baseUrl = "https://api.openweathermap.org/geo/1.0/reverse?appid=" + weatherCallback.getWeatherToken();

        coordsList.forEach(coord -> {
            String[] coords = coord.split(" ");
            String LAT = coords[0];
            String LON = coords[1];

            String url = baseUrl +
                    "&lat=" + LAT + "&lon=" + LON;
            Root[] root = JsonUtil.readValue(url, Root[].class);
            assert root != null;
            String city = root[0].getLocal_names().getRu().equals("Пермский городской округ") ? "Пермь" : root[0].getLocal_names().getRu();
            CITIES_NAMES.put(coord, city);
        });
    }
}
