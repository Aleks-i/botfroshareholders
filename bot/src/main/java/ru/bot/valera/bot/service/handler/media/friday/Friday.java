package ru.bot.valera.bot.service.handler.media.friday;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.service.handler.AbstractContent;
import ru.bot.valera.bot.service.handler.media.MediaStorage;
import ru.bot.valera.bot.to.UpdateTO;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

import static ru.bot.valera.bot.model.Command.FRIDAY;
import static ru.bot.valera.bot.util.WordsUtil.FRIDAY_P_1;
import static ru.bot.valera.bot.util.WordsUtil.GOOD_MORNING_NIGHT_FRIDAY_P_2;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Friday extends AbstractContent {

    final MediaStorage mediaStorage;

    @Override
    public Content handle(UpdateTO updateTO) {
        String textMessage = getRandomElementFromList(FRIDAY_P_1) + getRandomElementFromList(GOOD_MORNING_NIGHT_FRIDAY_P_2);
        return getVideoContent(getFile(), updateTO, textMessage);
    }

    private File getFile() {
        List<String> filePaths = mediaStorage.getMEDIA_STORAGE_PATHS().get(mediaStorage.getSOURCE_PATH_FRIDAY());
        return new File(Paths.get(getRandomElementFromList(filePaths)).toUri());
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(FRIDAY);
    }
}
