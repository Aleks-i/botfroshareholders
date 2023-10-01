package ru.bot.valera.bot.service.handlers.media.download;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.games.Animation;
import ru.bot.valera.bot.bot.Bot;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.to.UpdateTo;

import java.io.File;
import java.util.Set;

import static ru.bot.valera.bot.model.Command.FROM_ME_ANIMATION;
import static ru.bot.valera.bot.service.handlers.media.MimeType.GIF;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FromMeAnimation extends AbstractDownloadContent {

    public FromMeAnimation(Bot bot) {
        super(bot);
    }

    @Override
    public Content handle(UpdateTo updateTo) {
        log.info("FromMeAnimationHandler");
        return downloadAnimationContent(updateTo);
    }

    protected Content downloadAnimationContent(UpdateTo updateTo) {

        Message message = updateTo.getMessage();
        File file;
        Animation animation = message.getAnimation();
        if (animation != null) {
            animation.setMimetype(GIF.getType());
            file = downloadFile(animation.getFileId(), GIF.getType());
        } else {
            file = downloadFile(message.getDocument().getThumbnail().getFileId(), GIF.getType());
        }

        if (file != null) {
            return getAnimationContent(file, updateTo, message.getCaption());
        }
        return new Content(new SendMessage(), Command.NONE);
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(FROM_ME_ANIMATION);
    }
}
