package ru.bot.valera.bot.service.handlers.media.mans;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.service.handlers.media.AbstractMediaContent;
import ru.bot.valera.bot.service.handlers.media.SourcePaths;
import ru.bot.valera.bot.to.UpdateTo;

import java.util.Set;

import static ru.bot.valera.bot.model.Command.MANS_VIDEO;
import static ru.bot.valera.bot.model.SourceMessageType.TASK;

@Component
@RequiredArgsConstructor
public class MansVideo extends AbstractMediaContent {

    @Override
    public Content handle(UpdateTo updateTo) {
        if (updateTo.getSourceMessageType() == TASK) {
            return getVideoContent(getFileFromStorage(SourcePaths.SOURCE_PATH_MANS_VIDEO.getSourcePath()),
                    updateTo, "Мальчик дня");
        }
        return getVideoContent(getFileFromStorage(SourcePaths.SOURCE_PATH_MANS_VIDEO.getSourcePath()),
                updateTo);
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(MANS_VIDEO);
    }
}
