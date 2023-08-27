package ru.valera.bot.service.mailer.titsgif;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import ru.valera.bot.model.Content;
import ru.valera.bot.model.chat.MailerType;
import ru.valera.bot.service.mailer.MailerJob;
import ru.valera.bot.to.MessageType;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static ru.valera.bot.bot.Bot.CHAT_STORAGE;
import static ru.valera.bot.bot.MessageSender.sendQueue;
import static ru.valera.bot.service.handler.media.MediaStorage.MEDIA_STORAGE_PATHS;
import static ru.valera.bot.service.handler.media.MediaStorage.SOURCE_PATH_GIF;
import static ru.valera.bot.util.MessageUtil.getRandomListIdx;

@Slf4j
@Component
public class TitsGifJob implements MailerJob {

    public void execute(JobExecutionContext context) {
        List<String> filePaths = MEDIA_STORAGE_PATHS.get(SOURCE_PATH_GIF);

        AtomicReference<File> file = new AtomicReference<>();
        AtomicReference<SendAnimation> sendAnimation = new AtomicReference<>();
        CHAT_STORAGE.values().forEach(chat -> {
                    file.set(new File(Paths.get(filePaths.get(getRandomListIdx(filePaths.size()))).toUri()));
                    sendAnimation.set(new SendAnimation());
                    sendAnimation.get().setAnimation(new InputFile(file.get()));
                    sendAnimation.get().setChatId(chat.getChatId());
                    Content content = new Content(sendAnimation.get(), MessageType.TITS_GIF);
                    sendQueue.add(content);
                }
        );
        System.gc();
    }

    @Override
    public MailerType getSenderType() {
        return MailerType.TITS_GIF;
    }
}
