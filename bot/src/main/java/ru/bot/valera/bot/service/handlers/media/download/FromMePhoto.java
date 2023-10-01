package ru.bot.valera.bot.service.handlers.media.download;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import ru.bot.valera.bot.bot.Bot;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.to.UpdateTo;

import java.io.File;
import java.util.List;
import java.util.Set;

import static ru.bot.valera.bot.model.Command.FROM_ME_PHOTO;
import static ru.bot.valera.bot.service.handlers.media.MimeType.JPEG;

@Slf4j
@Component
public class FromMePhoto extends AbstractDownloadContent {

    public FromMePhoto(Bot bot) {
        super(bot);
    }

    @Override
    public Content handle(UpdateTo updateTo) {
        log.info("FromMePhotoHandler");
        return downloadPhotoContent(updateTo);
    }

    protected Content downloadPhotoContent(UpdateTo updateTo) {

        Message message = updateTo.getMessage();

        List<PhotoSize> photoSizes = message.getPhoto();
        File file = downloadFile(photoSizes.get(photoSizes.size() - 1).getFileId(), JPEG.getType());
        if (file != null) {
            return getPhotoContent(file, updateTo, message.getCaption());
        }
        return new Content(new SendPhoto(), Command.NONE);
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(FROM_ME_PHOTO);
    }
}
