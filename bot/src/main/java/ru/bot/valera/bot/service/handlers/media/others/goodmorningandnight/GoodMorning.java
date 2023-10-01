package ru.bot.valera.bot.service.handlers.media.others.goodmorningandnight;

import com.vdurmont.emoji.EmojiManager;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.model.EmojiEnum;
import ru.bot.valera.bot.model.TextMessage;
import ru.bot.valera.bot.service.handlers.media.AbstractMediaContent;
import ru.bot.valera.bot.to.UpdateTo;
import ru.bot.valera.bot.util.JsonUtil;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static ru.bot.valera.bot.model.Command.GOOD_MORNING;
import static ru.bot.valera.bot.model.EmojiEnum.FIST;
import static ru.bot.valera.bot.model.EmojiEnum.RU;
import static ru.bot.valera.bot.model.SourceMessageType.TASK;
import static ru.bot.valera.bot.service.handlers.media.SourcePaths.SOURCE_PATH_GOOD_MORNING;
import static ru.bot.valera.bot.util.WordsUtil.*;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GoodMorning extends AbstractMediaContent {

    final Map<LocalDate, String> localDateMessageMap = new ConcurrentHashMap<>();

    static final String URL = "http://localhost:8091/api/text/holidays";

    @Override
    public Content handle(UpdateTo updateTo) {
        if (updateTo.getSourceMessageType() == TASK) {
            String textMessage = getTextMessage();
            if (textMessage.isEmpty()) {
                return new Content(new SendVideo(), Command.NONE);
            }
            return getVideoContent(getFileFromStorage(SOURCE_PATH_GOOD_MORNING.getSourcePath()), updateTo, textMessage);
        } else {
            return getVideoContent(getFileFromStorage(SOURCE_PATH_GOOD_MORNING.getSourcePath()), updateTo, "Держи позитив чик");
        }
    }

    private String getTextMessage() {
        LocalDate localDateNow = LocalDate.now();
        if (localDateMessageMap.get(localDateNow) == null) {
            localDateMessageMap.clear();
            while (localDateNow.isEqual(LocalDate.now())) {
                TextMessage textMessage = JsonUtil.readValue(URL, TextMessage.class);

                if (textMessage != null && !textMessage.getText().isEmpty()) {
                    localDateMessageMap.put(localDateNow, formatTextMessage(textMessage.getText().replace("#праздники", "")));
                    return getRandomElementFromList(GOOD_MORNING_P_1) +
                            getRandomElementFromList(GOOD_MORNING_NIGHT_FRIDAY_P_2) +
                            localDateMessageMap.get(localDateNow);
                }
                try {
                    TimeUnit.MINUTES.sleep(10);
                } catch (InterruptedException e) {
                    log.error("Take interrupt while operate msg list", e);
                    break;
                }
            }
            return "";
        }
        return getRandomElementFromList(GOOD_MORNING_P_1) +
                getRandomElementFromList(GOOD_MORNING_NIGHT_FRIDAY_P_2) + localDateMessageMap.get(localDateNow);
    }

    private String formatTextMessage(String string) {
        StringBuilder sb = new StringBuilder();
        String[] str = string.split("\n");
        for (int i = 0; i < str.length; i++) {
            if (i == 0) {
                String emojiRU = getEmoji(RU);
                sb.append(emojiRU).append(emojiRU).append(emojiRU).append("\n");
            } else if (i == 2) {
                sb.append("_ __").append(str[i]).append("__ _").append("\n");
            } else if (i == str.length - 1) {
                String emojiFist = getEmoji(FIST);
                sb.append(getRandomElementFromList(GOOD_MORNING_END_LINE)).append(emojiFist);
            } else {
                sb.append(str[i]).append("\n");
            }
        }
        return sb.toString();
    }

    private String getEmoji(EmojiEnum emoji) {
        return EmojiManager.getForAlias(emoji.getTitle()).getUnicode();
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(GOOD_MORNING);
    }
}
