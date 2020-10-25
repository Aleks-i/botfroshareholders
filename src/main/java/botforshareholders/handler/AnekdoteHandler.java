package botforshareholders.handler;

import botforshareholders.bot.Bot;
import botforshareholders.command.ParsedCommand;
import botforshareholders.repository.AnekdoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static botforshareholders.util.AnekdoteUtil.getRandomAnekdoteId;

@Component
public class AnekdoteHandler extends AbstractHandler {
    private static final Logger LOG = LoggerFactory.getLogger(AnekdoteHandler.class);

    private final AnekdoteRepository anekdoteRepository;

    public AnekdoteHandler(Bot bot, AnekdoteRepository anekdoteRepository) {
        super(bot);
        this.anekdoteRepository = anekdoteRepository;
    }

    @Override
    public String operate(String chatId, ParsedCommand parsedCommand, Update update) {
        bot.sendQueue.add(getMessageForAnekdote(chatId, update.getMessage().getMessageId()));
        return "";
    }

    private SendMessage getMessageForAnekdote(String chatId, Integer messageId) {
        String textMessage = anekdoteRepository.getAnek(getRandomAnekdoteId()).getText();
        return getSendMessage(chatId, messageId, textMessage);
    }

    private SendMessage getSendMessage(String chatId, int messageId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setReplyToMessageId(messageId);
        sendMessage.setText(text);
        return sendMessage;
    }
}
