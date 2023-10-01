package ru.bot.valera.bot.service.handlers.media.others.rock;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.service.handlers.media.AbstractMediaContent;
import ru.bot.valera.bot.to.UpdateTo;

import java.util.Set;

import static ru.bot.valera.bot.model.Command.ROCK;
import static ru.bot.valera.bot.model.SourceMessageType.TASK;
import static ru.bot.valera.bot.service.handlers.media.SourcePaths.SOURCE_PATH_ROCK;
import static ru.bot.valera.bot.util.WordsUtil.ROCK_CAPTION;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Rock extends AbstractMediaContent {

    @Override
    public Content handle(UpdateTo updateTo) {
        log.info("rock handler get update to");
        if (updateTo.getSourceMessageType() == TASK) {
            return getVideoContent(getFileFromStorage(SOURCE_PATH_ROCK.getSourcePath()), updateTo,
                    getRandomElementFromList(ROCK_CAPTION));
        } else {
            return getVideoContent(getFileFromStorage(SOURCE_PATH_ROCK.getSourcePath()), updateTo);
        }
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(ROCK);
    }
}
