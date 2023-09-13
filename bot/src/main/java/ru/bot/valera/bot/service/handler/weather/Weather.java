package ru.bot.valera.bot.service.handler.weather;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.service.handler.AbstractContent;
import ru.bot.valera.bot.service.keyboards.weathher.CallBackWeatherKeyboardStart;
import ru.bot.valera.bot.to.UpdateTO;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class Weather extends AbstractContent {

    private final CallBackWeatherKeyboardStart callBackWeatherKeyboardStart;

    @Override
    public Content handle(UpdateTO updateTO) {
        return getContent("Выбирай: ", updateTO, callBackWeatherKeyboardStart);
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(Command.WEATHER);
    }
}
