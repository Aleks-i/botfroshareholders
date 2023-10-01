package ru.bot.valera.bot.service.handlers.media.others.goodmorningandnight;

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

import static ru.bot.valera.bot.model.Command.GOOD_NIGHT;
import static ru.bot.valera.bot.model.SourceMessageType.TASK;
import static ru.bot.valera.bot.service.handlers.media.SourcePaths.SOURCE_PATH_GOOD_NIGHT;
import static ru.bot.valera.bot.util.WordsUtil.GOOD_MORNING_NIGHT_FRIDAY_P_2;
import static ru.bot.valera.bot.util.WordsUtil.GOOD_NIGHT_P_1;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GoodNight extends AbstractMediaContent {

    @Override
    public Content handle(UpdateTo updateTo) {
        if (updateTo.getSourceMessageType() == TASK) {
            String text = getRandomElementFromList(GOOD_NIGHT_P_1) +
                    getRandomElementFromList(GOOD_MORNING_NIGHT_FRIDAY_P_2);

            return getVideoContent(getFileFromStorage(SOURCE_PATH_GOOD_NIGHT.getSourcePath()), updateTo, text);
        }
        return getVideoContent(getFileFromStorage(SOURCE_PATH_GOOD_NIGHT.getSourcePath()),
                updateTo, "Лови, позитивчик");
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(GOOD_NIGHT);
    }
}
