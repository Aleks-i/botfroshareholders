package ru.bot.valera.bot.service.handlers.media.girls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.service.handlers.media.AbstractMediaContent;
import ru.bot.valera.bot.service.handlers.media.SourcePaths;
import ru.bot.valera.bot.to.UpdateTo;

import java.util.Set;

import static ru.bot.valera.bot.model.Command.TITS_VIDEO;
import static ru.bot.valera.bot.model.SourceMessageType.TASK;
import static ru.bot.valera.bot.service.handlers.media.MimeType.MP4;
import static ru.bot.valera.bot.util.WordsUtil.TITS_CAPTION;

@Component
@RequiredArgsConstructor
public class TitsVideo extends AbstractMediaContent {

    @Override
    public Content handle(UpdateTo updateTo) {
        if (updateTo.getSourceMessageType() == TASK) {
            return getVideoContent(getFileFromStorage(SourcePaths.SOURCE_PATH_TITS_VIDEO.getSourcePath()),
                    updateTo, getRandomElementFromList(TITS_CAPTION));
        }
        return getVideoContent(getFileFromStorage(SourcePaths.SOURCE_PATH_TITS_VIDEO.getSourcePath()), updateTo);
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(TITS_VIDEO);
    }
}
