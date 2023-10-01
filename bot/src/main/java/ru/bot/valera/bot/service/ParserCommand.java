
package ru.bot.valera.bot.service;

import com.vdurmont.emoji.EmojiManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.bot.valera.bot.TelegramBotConfig;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.persist.chat.ChatType;
import ru.bot.valera.bot.to.UpdateTo;

import java.util.Arrays;

import static ru.bot.valera.bot.model.Command.*;
import static ru.bot.valera.bot.model.SourceMessageType.BOT;

@Slf4j
@Component
@RequiredArgsConstructor
public class ParserCommand {
    String DELIMITER_COMMAND_BOTNAME = "@";

    @Value("${bot.name}")
    private String botName;

    final TelegramBotConfig telegramBotConfig;
    final TelegramApi telegramApi;

    public UpdateTo getParsedUpdate(Update update) {
        UpdateTo updateTo = new UpdateTo(BOT, NONE);
        Message message;

        if (update.hasMessage()) {
            message = update.getMessage();
            if (message.getChat().isChannelChat()) {
                return updateTo;
            }
            updateTo.setUserIdFrom(message.getFrom().getId());

            if (message.getFrom().getId().equals(telegramBotConfig.getMY_TELEGRAM_ID())) {
                if (message.hasVideo()) {
                    updateTo.setCommand(FROM_ME_VIDEO);
                } else if (message.hasAnimation()) {
                    updateTo.setCommand(FROM_ME_ANIMATION);
                } else if (message.hasPhoto()) {
                    updateTo.setCommand(FROM_ME_PHOTO);
                } else if (message.hasText()) {
                    updateTo.setCommand(FROM_ME_TEXT);
                } else if (message.hasDocument()) {
                    updateTo.setCommand(DOCUMENT_FROM_ME);
                }
                updateTo.setChatId(telegramBotConfig.getMY_TELEGRAM_CHANNEL_ID());
                updateTo.setMessage(message);
            } else if (message.getNewChatMembers().size() != 0) {
                setIDsParserUpdate(message, updateTo);
                updateTo.setCommand(NEW_USER);
            } else if (message.getLeftChatMember() != null) {
                setIDsParserUpdate(message, updateTo);
                updateTo.setCommand(LEFT_USER);
            } else if (update.getMessage().hasText()) {
                Command command = getCommand(message.getText());
                if (command == TEXT_CONTAIN_EMOJI) {
                    updateTo.setChatType(ChatType.valueOf(message.getChat().getType().toUpperCase()));
                    setIDsParserUpdate(message, updateTo);
                    updateTo.setMessage(message);
                    //shows emoji id in chat after sending emoji in chat
                    //for work replace NONE to TEXT_CONTAIN_EMOJI
                    updateTo.setCommand(NONE);
                } else {
                    updateTo.setChatType(ChatType.valueOf(message.getChat().getType().toUpperCase()));
                    setIDsParserUpdate(message, updateTo);
                    updateTo.setCommand(getCommand(message.getText()));
                }
            } else if (update.getMessage().hasSticker()) {
                //shows sticker id in chat after sending sticker in chat
                //for work replace NONE to STICKER
                setIDsParserUpdate(message, updateTo);
                updateTo.setSticker(message.getSticker());
                updateTo.setCommand(NONE);
            }
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            if (callbackQuery.getMessage().getChat().isChannelChat()) {
                return updateTo;
            }

            long userIdFrom = callbackQuery.getFrom().getId();
            if (telegramApi.isGroupMember(callbackQuery.getMessage().getChatId(), userIdFrom)) {

                message = callbackQuery.getMessage();
                setIDsParserUpdate(message, updateTo);
                updateTo.setUserIdFrom(userIdFrom);

                String[] callBackDataArr = callbackQuery.getData().split("-");
                updateTo.setCallBackData(Arrays.stream(callBackDataArr).skip(1L).toArray(String[]::new));

                updateTo.setCommand(Command.valueOf(callBackDataArr[0]));
            }
        }

        return updateTo;
    }

    private void setIDsParserUpdate(Message message, UpdateTo updateTo) {
        updateTo.setChatId(message.getChatId());
        updateTo.setMessageId(message.getMessageId());
        updateTo.setUserName(message.getChat().getUserName());
        updateTo.setFirstName(message.getChat().getFirstName());
        updateTo.setLastName(message.getChat().getLastName());
    }

    private Command getCommand(String text) {
        String message = text.toLowerCase();

        if (!isCommandForMe(message)) {
            return Command.NONE;
        }
        if (isMessageIsCommand(message)) {
            if (message.contains(DELIMITER_COMMAND_BOTNAME) && message.substring(0, message.indexOf(DELIMITER_COMMAND_BOTNAME)).equals("/help") ||
                    message.equals("/help")) {
                return HELP;
            } else if (message.contains(DELIMITER_COMMAND_BOTNAME) && message.substring(0, message.indexOf(DELIMITER_COMMAND_BOTNAME)).equals("/start") ||
                    message.equals("/start")) {
                return START;
            } else if (message.contains(DELIMITER_COMMAND_BOTNAME) && message.substring(0, message.indexOf(DELIMITER_COMMAND_BOTNAME)).equals("/end") ||
                    message.equals("/end")) {
                return END;
            }
        }

        if (message.contains("валера") || message.contains("валерий") || message.contains("валерчик") || message.contains("валерон") ||
                message.contains("валерьяныч") || message.contains("валерка") || message.contains("валер") || message.contains("валяська")) {
            return VALERA;
        } else if (message.contains("что нибудь еще")) {
            return STATUSES;
        } else if ((message.contains("курс") || message.contains("курсы")) && message.contains("валют")) {
            return CURRENCY;
        } else if (message.equals("девочки")) {
            return GIRLS;
        } else if (message.contains("горячие самсы")) {
            return MANS;
        } else if (message.contains("чё помайнить")) {
            return MINING;
        } else if (message.contains("другие видосики")) {
            return OTHER_VIDEO;
        } else if (message.contains("анекдот")) {
            return ANECDOTE;
        } else if (message.contains("статусы вконтакте")) {
            return STATUSES;
        } else if (message.contains("погода")) {
            return WEATHER;
        } else if (message.contains("привет") || message.contains("прювет") || message.contains("здарово") || message.contains("здарова") || message.contains("бонжур")
                || (message.contains("добрый") && (message.contains("день") || message.contains("вечер")))
                || (message.contains("доброе") && message.contains("утро"))
                || (message.contains("дароф") || message.contains("здароф"))) {
            return GREETING;
        } else if (message.contains("настройки бота и рассылки")) {
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
        String PREFIX_FOR_COMMAND = "/";
        return text.startsWith(PREFIX_FOR_COMMAND);
    }
}
