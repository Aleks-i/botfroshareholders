package ru.bot.valera.bot.service.handler.mining;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.service.handler.AbstractContent;
import ru.bot.valera.bot.service.keyboards.CallBackMiningKeyboard;
import ru.bot.valera.bot.to.UpdateTO;

import java.util.Set;

//minerstate
@Slf4j
@Component
@RequiredArgsConstructor
public class Mining extends AbstractContent {

    private final CallBackMiningKeyboard callBackMiningKeyboard;

    @Override
    public Content handle(UpdateTO updateTO) {
        return getContent("Пока в наличии только зеленые: ", updateTO, callBackMiningKeyboard);
    }


    @Override
    public Set<Command> getMessageType() {
        return Set.of(Command.MINING);
    }
}
