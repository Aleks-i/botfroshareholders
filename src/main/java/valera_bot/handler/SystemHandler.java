package valera_bot.handler;

import valera_bot.bot.Bot;
import valera_bot.command.Command;
import valera_bot.command.ParsedCommand;
import valera_bot.keyboard.MainKeyboard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static valera_bot.util.TextMessageForAnswer.*;

@Component
public class SystemHandler extends AbstractHandler {
    private static final Logger log = LoggerFactory.getLogger(SystemHandler.class);
    private final String END_LINE = "\n";

    private final MainKeyboard keyboard;

    public SystemHandler(Bot bot, MainKeyboard keyboard) {
        super(bot);
        this.keyboard = keyboard;
    }

    @Override
    public String operate(String chatId, ParsedCommand parsedCommand, Update update) {
        Command command = parsedCommand.getCommand();
        log.debug("add message to sendQueue for message {}", update.getMessage().getText());
        switch (command) {
            case GREETING -> Bot.sendQueue.add(getMessageGreating(chatId, update.getMessage().getMessageId()));
            case NEWSECTION -> Bot.sendQueue.add(getMessageNewSection(chatId, update.getMessage().getMessageId()));
            case VALERA -> Bot.sendQueue.add(getMessageForValeraWithKeyBoard(chatId, update.getMessage().getMessageId()));
            case CURRENCY -> Bot.sendQueue.add(getMessageForCurrency(chatId, update.getMessage().getMessageId()));
            case GIRLS -> Bot.sendQueue.add(getMessageForGirls(chatId, update.getMessage().getMessageId()));
            case MINING -> Bot.sendQueue.add(getMessageForMining(chatId, update.getMessage().getMessageId()));
            case STICKER -> {
                return "StickerID: " + parsedCommand.getText();
            }
        }
        return "";
    }

    private SendMessage getMessageGreating(String chatId, int messageId) {
        String textMessage = getTextAnswer(greetings);
        return getSendMessage(chatId, messageId,textMessage);
    }

    private SendMessage getMessageNewSection(String chatId, Integer messageId) {
        String textMessage = "А какой бы раздел ты хотел тут видеть? не вопрос, сделаем";
        return getSendMessage(chatId, messageId, textMessage);
    }

    private SendMessage getMessageForValeraWithKeyBoard(String chatId, Integer messageId) {
        String textMessage = getTextAnswer(answerForValera);
        return getMessageWithKeyboard(chatId, messageId, textMessage);
    }

    private SendMessage getMessageForCurrency(String chatId, Integer messageId) {
        String textMessage = "Сервис в разработке";
        return getSendMessage(chatId, messageId, textMessage);
    }

    private SendMessage getMessageForGirls(String chatId, Integer messageId) {
        String textMessage = "Пока не готово, но скоро голые бабы будут тут";
        return getSendMessage(chatId, messageId, textMessage);
    }

    private SendMessage getMessageForMining(String chatId, Integer messageId) {
        String textMessage = "Владос, чуть позже";
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

    private SendMessage getMessageWithKeyboard(String chatId, Integer messageId, String textMessage) {
        SendMessage sendMessage = getSendMessage(chatId,messageId, textMessage);
        keyboard.setReplyKeyboardMarkupMain(sendMessage);
        return sendMessage;
    }
}
