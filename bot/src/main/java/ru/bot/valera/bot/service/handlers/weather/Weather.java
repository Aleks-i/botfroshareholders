package ru.bot.valera.bot.service.handlers.weather;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.service.handlers.AbstractContent;
import ru.bot.valera.bot.service.keyboards.weathher.CallBackWeatherKeyboardStart;
import ru.bot.valera.bot.to.UpdateTo;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class Weather extends AbstractContent {

    private final CallBackWeatherKeyboardStart callBackWeatherKeyboardStart;

    @Override
    public Content handle(UpdateTo updateTo) {
        return getContent("Выбирай: ", updateTo, callBackWeatherKeyboardStart);
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(Command.WEATHER);
    }
}
