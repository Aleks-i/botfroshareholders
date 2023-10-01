package ru.bot.valera.bot.service.handlers.media.mans;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.service.handlers.AbstractContent;
import ru.bot.valera.bot.service.keyboards.CallBackMansKeyboard;
import ru.bot.valera.bot.to.UpdateTo;

import java.util.Set;

import static ru.bot.valera.bot.model.Command.MANS;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChapterMans extends AbstractContent {

    private final CallBackMansKeyboard callBackMansKeyboard;

    @Override
    public Content handle(UpdateTo updateTo) {
        return getContent("Что выберешь: ", updateTo, callBackMansKeyboard);
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(MANS);
    }
}
