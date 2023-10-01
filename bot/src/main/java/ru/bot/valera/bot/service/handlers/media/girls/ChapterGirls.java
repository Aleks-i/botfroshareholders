package ru.bot.valera.bot.service.handlers.media.girls;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.service.handlers.AbstractContent;
import ru.bot.valera.bot.service.keyboards.CallBackGirlsKeyboard;
import ru.bot.valera.bot.to.UpdateTo;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChapterGirls extends AbstractContent {

    private final CallBackGirlsKeyboard callBackGirlsKeyboard;

    @Override
    public Content handle(UpdateTo updateTo) {
        return getContent("Что выберешь: ", updateTo, callBackGirlsKeyboard);
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(Command.GIRLS);
    }
}
