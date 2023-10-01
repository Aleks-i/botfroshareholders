package ru.bot.valera.bot.service.handlers.media.girls;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.service.handlers.media.AbstractMediaContent;
import ru.bot.valera.bot.to.UpdateTo;

import java.util.Set;

import static ru.bot.valera.bot.model.Command.TITS_GIF;
import static ru.bot.valera.bot.model.SourceMessageType.TASK;
import static ru.bot.valera.bot.service.handlers.media.MimeType.GIF;
import static ru.bot.valera.bot.service.handlers.media.SourcePaths.SOURCE_PATH_TITS_GIF;
import static ru.bot.valera.bot.util.WordsUtil.TITS_CAPTION;

@Slf4j
@Component
@RequiredArgsConstructor
public class TitsGif extends AbstractMediaContent {

    @Override
    public Content handle(UpdateTo updateTo) {
        if (updateTo.getSourceMessageType() == TASK) {
            return getAnimationContent(getFileFromStorage(SOURCE_PATH_TITS_GIF.getSourcePath()), updateTo,
                    getRandomElementFromList(TITS_CAPTION));
        }
        return getAnimationContent(getFileFromStorage(SOURCE_PATH_TITS_GIF.getSourcePath()), updateTo);
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(TITS_GIF);
    }
}
