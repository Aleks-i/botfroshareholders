package ru.bot.valera.bot.service.handlers.media.mans;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.service.handlers.media.AbstractMediaContent;
import ru.bot.valera.bot.to.UpdateTo;

import java.util.Set;

import static ru.bot.valera.bot.model.Command.MANS_GIF;
import static ru.bot.valera.bot.model.SourceMessageType.TASK;
import static ru.bot.valera.bot.service.handlers.media.SourcePaths.SOURCE_PATH_MANS_GIF;
import static ru.bot.valera.bot.util.WordsUtil.MANS_CAPTION;

@Component
@RequiredArgsConstructor
public class MansGif extends AbstractMediaContent {

    @Override
    public Content handle(UpdateTo updateTo) {
        if (updateTo.getSourceMessageType() == TASK) {
            return getAnimationContent(getFileFromStorage(SOURCE_PATH_MANS_GIF.getSourcePath()), updateTo,
                    getRandomElementFromList(MANS_CAPTION));
        }
        return getAnimationContent(getFileFromStorage(SOURCE_PATH_MANS_GIF.getSourcePath()), updateTo);
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(MANS_GIF);
    }
}
