package ru.bot.valera.bot.service.handlers.mining;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.service.handlers.AbstractContent;
import ru.bot.valera.bot.service.keyboards.CallBackMiningKeyboard;
import ru.bot.valera.bot.to.UpdateTo;

import java.util.Set;

//minerstate
@Slf4j
@Component
@RequiredArgsConstructor
public class Mining extends AbstractContent {

    private final CallBackMiningKeyboard callBackMiningKeyboard;

    @Override
    public Content handle(UpdateTo updateTo) {
        return getContent("Пока в наличии только зеленые: ", updateTo, callBackMiningKeyboard);
    }


    @Override
    public Set<Command> getMessageType() {
        return Set.of(Command.MINING);
    }
}
