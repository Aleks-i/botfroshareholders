package ru.valera.bot.service.mailer.titsgirlsvideo;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
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
import static ru.valera.bot.service.handler.media.MediaStorage.SOURCE_PATH_TITS_VIDEO;
import static ru.valera.bot.util.MessageUtil.getRandomListIdx;

@Slf4j
@Component
public class TitsVideoJob implements MailerJob {

    public void execute(JobExecutionContext context) {
        List<String> filePaths = MEDIA_STORAGE_PATHS.get(SOURCE_PATH_TITS_VIDEO);

        AtomicReference<File> file = new AtomicReference<>();
        AtomicReference<SendVideo> sendVideoAtomicReference = new AtomicReference<>();

//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setText("рассылка началась");
//        sendMessage.setChatId(517301258L);
//        Content content1 = new Content(sendMessage, MessageType.HELP);
//        sendQueue.add(content1);

//        for (int i = 0; i < 1_000_000; i++) {
//            file.set(new File(Paths.get(filePaths.get(getRandomListIdx(filePaths.size()))).toUri()));
////            sendVideoAtomicReference.set(new SendVideo());
//            sendVideoAtomicReference.get().setVideo(new InputFile(file.get()));
//            sendVideoAtomicReference.get().setChatId(316445085L);
//            Content content = new Content(sendVideoAtomicReference.get(), MessageType.TITS_VIDEO);
//            log.info(String.valueOf(i));
//            sendQueue.add(content);
//        }
//        sendMessage = new SendMessage();
//        sendMessage.setText("рассылка закончилась");
//        sendMessage.setChatId(517301258L);
//        content1 = new Content(sendMessage, MessageType.HELP);
//        sendQueue.add(content1);

        CHAT_STORAGE.values().forEach(chat -> {
                    file.set(new File(Paths.get(filePaths.get(getRandomListIdx(filePaths.size()))).toUri()));
                    sendVideoAtomicReference.set(new SendVideo());
                    sendVideoAtomicReference.get().setVideo(new InputFile(file.get()));
                    sendVideoAtomicReference.get().setChatId(chat.getChatId());
                    Content content = new Content(sendVideoAtomicReference.get(), MessageType.TITS_VIDEO);
                    sendQueue.add(content);
                }
        );
        System.gc();
    }

    @Override
    public MailerType getSenderType() {
        return MailerType.TITS_VIDEO;
    }
}
