package ru.bot.valera.bot.service.handlers.media;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.service.handlers.AbstractContent;
import ru.bot.valera.bot.to.UpdateTo;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static ru.bot.valera.bot.model.Command.GOOD_MORNING;
import static ru.bot.valera.bot.model.SourceMessageType.TASK;

@Slf4j
@Getter
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class AbstractMediaContent extends AbstractContent {

    public static final Map<String, List<String>> MEDIA_STORAGE_PATHS = new ConcurrentHashMap<>();
//    public static final String ROOT_PATH = "E:/Programs/valerabot/";
    public static final String ROOT_PATH = "C:/Server/data/app/valerabot/";

    static volatile int counterContentName = 0;

    protected Content getVideoContent(File file, UpdateTo updateTO) {
        SendVideo sendVideo = new SendVideo(String.valueOf(updateTO.getChatId()), new InputFile(file));
        sendVideo.setReplyToMessageId(updateTO.getMessageId());
        return new Content(sendVideo, updateTO.getCommand());
    }

    protected Content getVideoContent(File file, UpdateTo updateTo, String text) {
        SendVideo sendVideo = new SendVideo(String.valueOf(updateTo.getChatId()), new InputFile(file));
        sendVideo.setCaption(text);
        if (updateTo.getCommand() == GOOD_MORNING && updateTo.getSourceMessageType() == TASK) {
            formatAndSetMarkdownV2ForSendVideo(sendVideo);
        } else {
            sendVideo.setParseMode("Markdown");
        }
        return new Content(sendVideo, updateTo.getCommand());
    }

    protected Content getAnimationContent(File file, UpdateTo updateTo) {
        SendAnimation sendAnimation = new SendAnimation(String.valueOf(updateTo.getChatId()), new InputFile(file));
        sendAnimation.setReplyToMessageId(updateTo.getMessageId());
        return new Content(sendAnimation, updateTo.getCommand());
    }

    protected Content getAnimationContent(File file, UpdateTo updateTO, String text) {
        SendAnimation sendAnimation = new SendAnimation(String.valueOf(updateTO.getChatId()), new InputFile(file));
        sendAnimation.setCaption(text);
        return new Content(sendAnimation, updateTO.getCommand());
    }

    protected Content getPhotoContent(File file, UpdateTo updateTo) {
        SendPhoto photo = new SendPhoto(String.valueOf(updateTo.getChatId()), new InputFile(file));
        photo.setReplyToMessageId(updateTo.getMessageId());
        return new Content(photo, updateTo.getCommand());
    }

    protected Content getPhotoContent(File file, UpdateTo updateTo, String text) {
        SendPhoto photo = new SendPhoto(String.valueOf(updateTo.getChatId()), new InputFile(file));
        photo.setCaption(text);
        return new Content(photo, updateTo.getCommand());
    }

    public File getFileFromStorage(String sourcePath) {
        log.info("get file from storage");
        List<String> mediaFilePaths = MEDIA_STORAGE_PATHS.get(ROOT_PATH + sourcePath);
        return new File(Paths.get(getRandomElementFromList(mediaFilePaths)).toUri());
    }

    public synchronized String getFileName(String mimeTypes) {
        if (counterContentName == 5_000) {
            counterContentName = 0;
        }
        String[] mimeTypeArr = mimeTypes.split("/");
        counterContentName++;
        return "tmp/valerrianych_bot-" + mimeTypeArr[0] + "-" + counterContentName + "-" +
                LocalDate.now() + "." + mimeTypeArr[1];
    }
}
