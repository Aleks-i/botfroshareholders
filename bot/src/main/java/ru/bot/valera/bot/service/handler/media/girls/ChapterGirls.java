package ru.bot.valera.bot.service.handler.media.girls;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.service.handler.AbstractContent;
import ru.bot.valera.bot.service.keyboards.CallBackGirlsKeyboard;
import ru.bot.valera.bot.to.UpdateTO;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChapterGirls extends AbstractContent {

    private final CallBackGirlsKeyboard callBackGirlsKeyboard;

    @Override
    public Content handle(UpdateTO updateTO) {
        return getContent("Что выберешь: ", updateTO, callBackGirlsKeyboard);
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(Command.GIRLS);
    }
}
