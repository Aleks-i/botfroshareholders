package ru.valera.bot.service.mailer.holidays;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;
import ru.valera.bot.model.chat.MailerType;
import ru.valera.bot.service.mailer.MailerJob;

import java.time.LocalDate;

@Slf4j
@Component
public class HolidaysJob implements MailerJob {

    private static final Long CHAT_ID_CHANEL_VEZHLIVYE_ZLYE = -1001512835502L;

//    @Autowired
//    TelegramClientService telegramClientService;

    public void execute(JobExecutionContext context) {
        LocalDate localDate = LocalDate.now();
        String text;
//        while (localDate.isEqual(LocalDate.now())) {
//            text = telegramClientService.getMessageTarget().get(MessageType.HOLIDAYS);
//            if (!text.isEmpty()) {
//                String finalText = text;
//                CHAT_STORAGE.values().forEach(chat -> {
//                            sendQueue.add(getContent(finalText.replace("#праздники", ""),
//                                    new UpdateTO(ru.valera.bot.to.MessageType.CUSTOM, chat.getChatId())));
//                        }
//                );
//                System.gc();
//            }
//            try {
//                TimeUnit.MINUTES.sleep(10);
//            } catch (InterruptedException e) {
//                log.error("Take interrupt while operate msg list", e);
//            }
//        }
    }

    @Override
    public MailerType getSenderType() {
        return MailerType.HOLIDAYS;
    }
}
