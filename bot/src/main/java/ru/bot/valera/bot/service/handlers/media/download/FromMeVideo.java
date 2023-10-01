package ru.bot.valera.bot.service.handlers.media.download;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bot.valera.bot.bot.Bot;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.to.UpdateTo;

import java.io.File;
import java.util.Set;

import static ru.bot.valera.bot.model.Command.FROM_ME_VIDEO;
import static ru.bot.valera.bot.service.handlers.media.MimeType.MP4;

@Slf4j
@Component
public class FromMeVideo extends AbstractDownloadContent {

    public FromMeVideo(Bot bot) {
        super(bot);
    }

    @Override
    public Content handle(UpdateTo updateTo) {
        log.info("FromMeVideoHandler");
        Message message = updateTo.getMessage();
        File file = downloadFile(message.getVideo().getFileId(), MP4.getType());

        if (file != null) {
            return getVideoContent(file, updateTo, message.getCaption());
        }
        return new Content(new SendMessage(), Command.NONE);
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(FROM_ME_VIDEO);
    }
}
