package ru.bot.valera.bot.service.handler.media.mans;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.service.handler.AbstractContent;
import ru.bot.valera.bot.service.handler.media.MediaStorage;
import ru.bot.valera.bot.to.UpdateTO;

import java.util.Set;

import static ru.bot.valera.bot.model.Command.MANS_GIF;
import static ru.bot.valera.bot.util.WordsUtil.MANS_CAPTION;

@Component
@RequiredArgsConstructor
public class MansGif extends AbstractContent {

    private final MediaStorage mediaStorage;

    @Override
    public Content handle(UpdateTO updateTO) {
        if (updateTO.getMessageId() == 0) {
            return getAnimationContent(mediaStorage.getFile(mediaStorage.getSOURCE_PATH_MANS_GIF()), updateTO,
                    getRandomElementFromList(MANS_CAPTION));
        }
        return getAnimationContent(mediaStorage.getFile(mediaStorage.getSOURCE_PATH_MANS_GIF()), updateTO);
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(MANS_GIF);
    }
}
