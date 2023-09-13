package ru.bot.valera.bot.service.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.model.TextMessage;
import ru.bot.valera.bot.to.UpdateTO;
import ru.bot.valera.bot.util.JsonUtil;

import java.time.LocalDate;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class Horoscope extends AbstractContent {

    private static final String URL = "http://localhost:8080/api/text/horoscope";

    @Override
    public Content handle(UpdateTO updateTO) {
        String textMessage = getTextMessage();
        if (textMessage.isEmpty()) {
            return new Content(new SendMessage(), Command.NONE);
        }
        return getContent(textMessage, updateTO);
    }

    private String getTextMessage() {

        LocalDate localDate = LocalDate.now();

        while (localDate.isEqual(LocalDate.now())) {
            TextMessage textMessage = JsonUtil.readValue(URL, TextMessage.class);

            if (textMessage != null && !textMessage.getText().isEmpty()) {
                return "*Гороскоп от нейросети: *\n\n" + textMessage.getText();
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

    @Override
    public Set<Command> getMessageType() {
        return Set.of(Command.HOROSCOPE);
    }
}
