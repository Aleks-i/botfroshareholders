
package ru.bot.valera.bot.service;

import com.vdurmont.emoji.EmojiManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.persist.chat.ChatType;
import ru.bot.valera.bot.to.UpdateTO;

import java.util.Arrays;

import static ru.bot.valera.bot.model.Command.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ParserCommand {
    private final String PREFIX_FOR_COMMAND = "/";
    String DELIMITER_COMMAND_BOTNAME = "@";

    @Value("${bot.name}" )
    private String botName;

    public UpdateTO getParsedUpdate(Update update) {
        log.info("parser command get update {}", update);
        UpdateTO updateTO = new UpdateTO(NONE);

        Message message = update.getMessage();
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            message = callbackQuery.getMessage();

            setIDsParserUdate(message, updateTO);
            updateTO.setUserIdFrom(callbackQuery.getFrom().getId());

            String[] callBackDataPars = callbackQuery.getData().split("-" );
            updateTO.setCallBackData(Arrays.stream(callBackDataPars).skip(1L).toArray(String[]::new));

            updateTO.setCommand(Command.valueOf(callBackDataPars[0]));
        } else if (update.getMessage() != null && message.getNewChatMembers().size() != 0) {
            setIDsParserUdate(message, updateTO);
            updateTO.setCommand(NEW_USER);
        } else if (update.getMessage() != null && message.getLeftChatMember() != null) {
            setIDsParserUdate(message, updateTO);
            updateTO.setCommand(LEFT_USER);
        } else if (message != null && update.getMessage().hasText()) {
            Command command = getCommand(message.getText());
            if (command == TEXT_CONTAIN_EMOJI) {
                updateTO.setChatType(ChatType.valueOf(message.getChat().getType().toUpperCase()));
                setIDsParserUdate(message, updateTO);
                updateTO.setMessage(message);
                //shows emoji id in chat after sending emoji in chat
                //for work replace NONE to TEXT_CONTAIN_EMOJI
                updateTO.setCommand(NONE);
            } else {
                updateTO.setChatType(ChatType.valueOf(message.getChat().getType().toUpperCase()));
                setIDsParserUdate(message, updateTO);
                updateTO.setCommand(getCommand(message.getText()));
            }
        } else if (message != null && update.getMessage().hasSticker()) {
            //shows sticker id in chat after sending sticker in chat
            //for work replace NONE to STICKER
            setIDsParserUdate(message, updateTO);
            updateTO.setSticker(message.getSticker());
            updateTO.setCommand(NONE);
        }
        return updateTO;
    }

    private String[] parse(String data) {
        return data.split("-" );
    }

    private void setIDsParserUdate(Message message, UpdateTO updateTO) {
        updateTO.setChatId(message.getChatId());
        updateTO.setMessageId(message.getMessageId());
    }

    private Command getCommand(String text) {
        String message = text.toLowerCase();

        if (!isCommandForMe(message)) {
            return Command.NONE;
        }
        if (isMessageIsCommand(message)) {
            if (message.contains(DELIMITER_COMMAND_BOTNAME) && message.substring(0, message.indexOf(DELIMITER_COMMAND_BOTNAME)).equals("/help" ) ||
                    message.equals("/help" )) {
                return HELP;
            } else if (message.contains(DELIMITER_COMMAND_BOTNAME) && message.substring(0, message.indexOf(DELIMITER_COMMAND_BOTNAME)).equals("/start" ) ||
                    message.equals("/start" )) {
                return START;
            } else if (message.contains(DELIMITER_COMMAND_BOTNAME) && message.substring(0, message.indexOf(DELIMITER_COMMAND_BOTNAME)).equals("/end" ) ||
                    message.equals("/end" )) {
                return END;
            }
        }

        if (message.contains("валера" ) || message.contains("валерий" ) || message.contains("валерчик" ) || message.contains("валерон" ) ||
                message.contains("валерьяныч" ) || message.contains("валерка" ) || message.contains("валер" ) || message.contains("валяська" )) {
            return VALERA;
        } else if (message.contains("что нибудь еще" )) {
            return STATUSES;
        } else if ((message.contains("курс" ) || message.contains("курсы" )) && message.contains("валют" )) {
            return CURRENCY;
        } else if (message.equals("девочки" )) {
            return GIRLS;
        } else if (message.contains("горячие самсы" )) {
            return MANS;
        } else if (message.contains("чё помайнить" )) {
            return MINING;
        } else if (message.contains("анекдот" )) {
            return ANECDOTE;
        } else if (message.contains("статусы вконтакте" )) {
            return STATUSES;
        } else if (message.contains("погода" )) {
            return WEATHER;
        } else if (message.contains("привет" ) || message.contains("прювет" ) || message.contains("здарово" ) || message.contains("здарова" ) || message.contains("бонжур" )
                || (message.contains("добрый" ) && (message.contains("день" ) || message.contains("вечер" )))
                || (message.contains("доброе" ) && message.contains("утро" ))
                || (message.contains("дароф" ) || message.contains("здароф" ))) {
            return GREETING;
        } else if (message.contains("настройки бота и рассылки" )) {
            return SETTINGS;
        } else if (EmojiManager.containsEmoji(message)) {
            return TEXT_CONTAIN_EMOJI;
        } else {
            return NONE;
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
        return text.startsWith("/" );
    }
}
