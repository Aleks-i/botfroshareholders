package ru.bot.valera.bot.service.handlers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.model.TextMessage;
import ru.bot.valera.bot.to.UpdateTo;
import ru.bot.valera.bot.util.JsonUtil;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Horoscope extends AbstractContent {

    static final String URL = "http://localhost:8091/api/text/horoscope";
    final Map<LocalDate, String> localDateMessageMap = new ConcurrentHashMap<>();

    @Override
    public Content handle(UpdateTo updateTo) {
        String textMessage = getTextMessage();
        if (textMessage.isEmpty()) {
            return new Content(new SendMessage(), Command.NONE);
        }
        return getContent(textMessage, updateTo);
    }

    private String getTextMessage() {
        LocalDate localDateNow = LocalDate.now();
        if (localDateMessageMap.get(localDateNow) == null) {
            localDateMessageMap.clear();

            while (localDateNow.isEqual(LocalDate.now())) {
                TextMessage textMessage = JsonUtil.readValue(URL, TextMessage.class);

                if (textMessage != null && !textMessage.getText().isEmpty()) {
                    localDateMessageMap.put(localDateNow, "*Гороскоп от нейросети: *\n\n" + textMessage.getText());
                    return localDateMessageMap.get(localDateNow);
                }
                try {
                    TimeUnit.MINUTES.sleep(10);
                } catch (InterruptedException e) {
                    log.error("Take interrupt while operate msg list", e);
                    break;
                }
            }
            return "";
        }
        return localDateMessageMap.get(localDateNow);
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(Command.HOROSCOPE);
    }
}
