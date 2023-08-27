package ru.valera.bot.service.handler.media;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import ru.valera.bot.model.Content;
import ru.valera.bot.service.handler.Handler;
import ru.valera.bot.to.MessageType;
import ru.valera.bot.to.UpdateTO;
import ru.valera.bot.util.MessageUtil;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

import static ru.valera.bot.service.handler.media.MediaStorage.*;

@Slf4j
@Component
public class CallBackMedia implements Handler {

    @Override
    public Content handle(UpdateTO updateTO) {
        MessageType messageType = updateTO.getMessageType();
        switch (messageType) {
            case TITS_GIF -> {
                List<String> filePaths = MEDIA_STORAGE_PATHS.get(SOURCE_PATH_GIF);
                File file = new File(Paths.get(filePaths.get(MessageUtil.getRandomListIdx(filePaths.size()))).toUri());
                SendAnimation sendAnimation = new SendAnimation();
                sendAnimation.setAnimation(new InputFile(file));
                return new Content(MessageUtil.setParamMessage(sendAnimation, updateTO), updateTO.getMessageType());
            }
            case TITS_VIDEO -> {
                SendVideo sendVideo = getSendVideo(SOURCE_PATH_TITS_VIDEO);
                return new Content(MessageUtil.setParamMessage(sendVideo, updateTO), updateTO.getMessageType());
            }
            case GIRLS_VIDEO -> {
                SendVideo sendVideo = getSendVideo(SOURCE_PATH_GIRLS_VIDEO);
                return new Content(MessageUtil.setParamMessage(sendVideo, updateTO), updateTO.getMessageType());
            }
            default -> {
                log.warn("Cant detect type of content {} in CallBackMediaHandler", updateTO.getMessageType());
                return null;
            }
        }
    }

    private SendVideo getSendVideo(String path) {
        List<String> filePaths = MEDIA_STORAGE_PATHS.get(path);
        File file = new File(Paths.get(filePaths.get(MessageUtil.getRandomListIdx(filePaths.size()))).toUri());
        SendVideo sendVideo = new SendVideo();
        sendVideo.setVideo(new InputFile(file));
        return sendVideo;
    }

    @Override
    public Set<MessageType> getMessageType() {
        return Set.of(MessageType.TITS_GIF, MessageType.TITS_VIDEO, MessageType.GIRLS_VIDEO);
    }
}
