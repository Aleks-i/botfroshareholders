package ru.bot.valera.telegram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bot.valera.telegram.model.TextMessage;
import ru.bot.valera.telegram.service.MessageType;
import ru.bot.valera.telegram.service.TelegramClientService;

import static ru.bot.valera.telegram.service.MessageType.*;

@RestController
@RequestMapping(value = "/api/text/", produces = MediaType.APPLICATION_JSON_VALUE)
public class Controller {

    @Autowired
    TelegramClientService telegramClientService;

    @GetMapping("horoscope" )
    public TextMessage horoscope() {
        return new TextMessage(getText(HOROSCOPE));
    }

    @GetMapping("holidays" )
    public TextMessage holidays() {
        return new TextMessage(getText(HOLIDAYS));
    }

    @GetMapping("main/day" )
    public TextMessage mainOfDay() {
        return new TextMessage(getText(MAIN_OF_DAY));
    }

    private String getText(MessageType messageType) {
        String text = telegramClientService.getMessageTarget().get(messageType);
        if (!text.isEmpty()) {
            telegramClientService.getMessageTarget().put(messageType, "" );
        }
        return text;
    }
}
