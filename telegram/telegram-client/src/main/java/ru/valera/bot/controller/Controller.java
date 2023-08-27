package ru.valera.bot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.valera.bot.service.MessageType;
import ru.valera.bot.service.TelegramClientService;

@RestController
@RequestMapping(value = "/api/text")
public class Controller {

    @Autowired
    TelegramClientService telegramClientService;

    @GetMapping(value = "/from/me")
    public String authorizationStatus() {
        return telegramClientService.getMessageTarget().get(MessageType.ME);
    }
}
