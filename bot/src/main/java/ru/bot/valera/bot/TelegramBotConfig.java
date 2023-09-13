package ru.bot.valera.bot;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramBotConfig {

    @Value("${bot.name}" )
    String botName;

    @Value("${bot.token}" )
    String botToken;
}
