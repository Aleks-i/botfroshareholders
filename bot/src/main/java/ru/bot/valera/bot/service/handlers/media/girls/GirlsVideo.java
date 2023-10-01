package ru.bot.valera.bot.service.handlers.media.girls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.service.handlers.media.AbstractMediaContent;
import ru.bot.valera.bot.to.UpdateTo;

import java.util.Set;

import static ru.bot.valera.bot.model.Command.GIRLS_VIDEO;
import static ru.bot.valera.bot.model.SourceMessageType.TASK;
import static ru.bot.valera.bot.service.handlers.media.SourcePaths.SOURCE_PATH_GIRLS_VIDEO;

@Component
@RequiredArgsConstructor
public class GirlsVideo extends AbstractMediaContent {

    @Override
    public Content handle(UpdateTo updateTo) {
        if (updateTo.getSourceMessageType() == TASK) {
            return getVideoContent(getFileFromStorage(SOURCE_PATH_GIRLS_VIDEO.getSourcePath()),
                    updateTo, "Девочка дня");
        }
        return getVideoContent(getFileFromStorage(SOURCE_PATH_GIRLS_VIDEO.getSourcePath()), updateTo);
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(GIRLS_VIDEO);
    }
}
