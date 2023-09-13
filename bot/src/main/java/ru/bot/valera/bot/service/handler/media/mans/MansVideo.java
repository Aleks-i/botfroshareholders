package ru.bot.valera.bot.service.handler.media.mans;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.service.handler.AbstractContent;
import ru.bot.valera.bot.service.handler.media.MediaStorage;
import ru.bot.valera.bot.to.UpdateTO;

import java.util.Set;

import static ru.bot.valera.bot.model.Command.MANS_VIDEO;

@Component
@RequiredArgsConstructor
public class MansVideo extends AbstractContent {

    private final MediaStorage mediaStorage;

    @Override
    public Content handle(UpdateTO updateTO) {
        if (updateTO.getMessageId() == 0) {
            return getVideoContent(mediaStorage.getFile(mediaStorage.getSOURCE_PATH_MANS_VIDEO()), updateTO, "Мальчик дня" );
        }
        return getVideoContent(mediaStorage.getFile(mediaStorage.getSOURCE_PATH_MANS_VIDEO()), updateTO);
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(MANS_VIDEO);
    }
}
