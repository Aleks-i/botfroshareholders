package ru.valera.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.valera.bot.model.chat.ChatType;
import ru.valera.bot.to.MessageType;
import ru.valera.bot.to.UpdateTO;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class ParserCommand {
    private final String PREFIX_FOR_COMMAND = "/";
    String DELIMITER_COMMAND_BOTNAME = "@";

    @Value("${bot.name}")
    private String botName;

    public UpdateTO getParsedUpdate(Update update) {
        Long chatId = 0L;
        UpdateTO updateTO = new UpdateTO(MessageType.DEFAULT);

        Message message = update.getMessage();
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            message = callbackQuery.getMessage();

            log.info("parsed update message: \"{}\", chatId: {}", message.getText(), message.getChatId());

            setIDsParserUdate(message, updateTO);

            String[] callBackDataPars = parse(callbackQuery.getData());
            updateTO.setCallBackData(Arrays.stream(callBackDataPars).skip(1L).toArray(String[]::new));

            updateTO.setMessageType(MessageType.valueOf(callBackDataPars[0]));
        } else if (update.getMessage() != null && message.getNewChatMembers().size() != 0) {
            setIDsParserUdate(message, updateTO);
            log.info("parsed update new user, chatId: {}", message.getChatId());
            updateTO.setMessageType(MessageType.NEW_USER);
        } else if (message != null && update.getMessage().hasText()) {
            updateTO.setChatType(ChatType.valueOf(message.getChat().getType().toUpperCase()));
            setIDsParserUdate(message, updateTO);
            log.info("parsed update message: \"{}\", chatId: {}", message.getText(), message.getChatId());
            updateTO.setMessageType(getCommand(message.getText()));
        } else if (message != null && update.getMessage().hasSticker()) {
            //shows sticker id in chat after sending sticker in chat
            //for work replace DEFAULT to STICKER
            setIDsParserUdate(message, updateTO);
            log.info("parsed update message: \"{}\", chatId: {}", message.getText(), message.getChatId());
            updateTO.setMessageType(MessageType.STICKER);
        }
        return updateTO;
    }

    private String[] parse(String data) {
        return data.split("-");
    }

    private void setIDsParserUdate(Message message, UpdateTO updateTO) {
        updateTO.setChatId(message.getChatId());
        updateTO.setMessageId(message.getMessageId());
    }

    private MessageType getCommand(String text) {
        List<String> words = getWordsFromMessage(text);

        if (!isCommandForMe(text)) {
            return MessageType.DEFAULT;
        }
        if (isMessageIsCommand(text)) {
            if (text.contains(DELIMITER_COMMAND_BOTNAME) && text.substring(0, text.indexOf(DELIMITER_COMMAND_BOTNAME)).equals("/help") ||
                    text.equals("/help")) {
                return MessageType.HELP;
            } else if (text.contains(DELIMITER_COMMAND_BOTNAME) && text.substring(0, text.indexOf(DELIMITER_COMMAND_BOTNAME)).equals("/start") ||
                    text.equals("/start")) {
                return MessageType.START;
            } else if (text.contains(DELIMITER_COMMAND_BOTNAME) && text.substring(0, text.indexOf(DELIMITER_COMMAND_BOTNAME)).equals("/end") ||
                    text.equals("/end")) {
                return MessageType.END;
            }
        }

        if (words.contains("валера") || words.contains("валерий") || words.contains("валерчик") || words.contains("валерон") ||
                words.contains("валерьяныч") || words.contains("валерка") || words.contains("валер") || words.contains("валяська")) {
            return MessageType.VALERA;
        } else if (words.contains("что нибудь еще")) {
            return MessageType.STATUSES_VK;
        } else if ((words.contains("курс") || words.contains("курсы")) && words.contains("валют")) {
            return MessageType.CURRENCY;
        } else if (words.size() == 1 && words.contains("девочки")) {
            return MessageType.GIRLS;
        } else if (words.contains("чё") && words.contains("помайнить")) {
            return MessageType.MINING;
        } else if (words.contains("анекдот")) {
            return MessageType.ANECDOTE;
        } else if (words.contains("статусы") && words.contains("вконтакте")) {
            return MessageType.STATUSES_VK;
        } else if (words.contains("погода")) {
            return MessageType.WEATHER;
        } else if (words.contains("привет") || words.contains("прювет") || words.contains("здарово") || words.contains("здарова") || words.contains("бонжур")
                || (words.contains("добрый") && (words.contains("день") || words.contains("вечер")))
                || (words.contains("доброе") && words.contains("утро"))
                || (words.contains("дароф") || words.contains("здароф"))) {
            return MessageType.GREETING;
        } else {
            return MessageType.DEFAULT;
        }
    }

    private boolean isCommandForMe(String text) {
        if (text.contains(DELIMITER_COMMAND_BOTNAME)) {
            String botNameForEqual = text.substring(text.indexOf(DELIMITER_COMMAND_BOTNAME) + 1);
            return botName.equals(botNameForEqual);
        }
        return true;
    }

    private boolean isMessageIsCommand(String text) {
        return text.startsWith("/");
    }

    public static List<String> getWordsFromMessage(String string) {
        List<String> list = Arrays.asList(string.split("[., !?:;-]").clone());
        list.replaceAll(String::toLowerCase);
        return list;
    }
}
