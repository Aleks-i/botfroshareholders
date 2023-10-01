package ru.bot.valera.bot.service.handlers.media.others;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.service.handlers.AbstractContent;
import ru.bot.valera.bot.service.keyboards.CallBackOthersVideosKeyboard;
import ru.bot.valera.bot.to.UpdateTo;

import java.util.Set;

import static ru.bot.valera.bot.model.Command.OTHER_VIDEO;

@Slf4j
@Component
@RequiredArgsConstructor
public class ElseVideos extends AbstractContent {

    final CallBackOthersVideosKeyboard callBackOthersVideosKeyboard;

    @Override
    public Content handle(UpdateTo updateTo) {
        log.info("else videos command: {}", updateTo.getCommand());
        return getContent("Ну смотри, на сегодня, это все чо у меня есть: ", updateTo, callBackOthersVideosKeyboard);
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(OTHER_VIDEO);
    }
}
