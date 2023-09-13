package ru.bot.valera.bot.service.handler.media.goodmorningandnight;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.model.TextMessage;
import ru.bot.valera.bot.service.handler.AbstractContent;
import ru.bot.valera.bot.service.handler.media.MediaStorage;
import ru.bot.valera.bot.to.UpdateTO;
import ru.bot.valera.bot.util.JsonUtil;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static ru.bot.valera.bot.model.Command.GOOD_MORNING;
import static ru.bot.valera.bot.model.EmojiEnum.RU;
import static ru.bot.valera.bot.util.WordsUtil.GOOD_MORNING_NIGHT_FRIDAY_P_2;
import static ru.bot.valera.bot.util.WordsUtil.GOOD_MORNING_P_1;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GoodMorning extends AbstractContent {

    final MediaStorage mediaStorage;

    static final String URL = "http://localhost:8080/api/text/holidays";

    @Override
    public Content handle(UpdateTO updateTO) {
        String textMessage = getTextMessage();
        if (textMessage.isEmpty()) {
            return new Content(new SendVideo(), Command.NONE);
        }
        return getVideoContent(getFile(), updateTO, textMessage);
    }

    private String getTextMessage() {
        LocalDate localDate = LocalDate.now();

        while (localDate.isEqual(LocalDate.now())) {
            TextMessage textMessage = JsonUtil.readValue(URL, TextMessage.class);

            if (textMessage != null && !textMessage.getText().isEmpty()) {
                return formatTextMessage(textMessage.getText().replace("#праздники", "" ));
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

    private String formatTextMessage(String string) {
        StringBuilder sb = new StringBuilder();
        String[] str = string.split("\n" );
        for (int i = 0; i < str.length; i++) {
            if (i == 0) {
                sb.append(getRandomElementFromList(GOOD_MORNING_P_1))
                        .append(getRandomElementFromList(GOOD_MORNING_NIGHT_FRIDAY_P_2))
                        .append(RU.getTitle()).append(RU.getTitle()).append(RU.getTitle()).append("\n\n" );
            } else if (i == 1) {
                sb.append("__" ).append(str[i]).append("__" ).append("\n\n\n" );
            } else if (i == str.length - 2) {
                sb.append(str[i]).append("\n\n" );
            } else if (i == str.length - 1) {
                sb.append("*" ).append(str[i]).append("*" ).append("\n" );
            } else {
                sb.append(str[i]).append("\n" );
            }
        }
        return sb.toString();
    }

    private File getFile() {
        List<String> filePaths = mediaStorage.getMEDIA_STORAGE_PATHS().get(mediaStorage.getSOURCE_PATH_GOOD_MORNING());
        return new File(Paths.get(getRandomElementFromList(filePaths)).toUri());
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(GOOD_MORNING);
    }
}
