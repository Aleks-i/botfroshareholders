package ru.bot.valera.bot.service.handler.media.mans;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.service.handler.AbstractContent;
import ru.bot.valera.bot.service.keyboards.CallBackMansKeyboard;
import ru.bot.valera.bot.to.UpdateTO;

import java.util.Set;

import static ru.bot.valera.bot.model.Command.MANS;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChapterMans extends AbstractContent {

    private final CallBackMansKeyboard callBackMansKeyboard;

    @Override
    public Content handle(UpdateTO updateTO) {
        return getContent("Что выберешь: ", updateTO, callBackMansKeyboard);
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(MANS);
    }
}
