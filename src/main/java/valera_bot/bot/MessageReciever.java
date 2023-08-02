package valera_bot.bot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;
import valera_bot.command.Command;
import valera_bot.command.ParsedCommand;
import valera_bot.command.Parser;
import valera_bot.handler.*;

import static valera_bot.bot.Bot.receiveQueue;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageReciever implements Runnable {
    private static final int WAIT_FOR_NEW_MESSAGE_DELAY = 100;

    private Bot bot;
    private final Parser parser;
    private final AnekdoteHandler anekdoteHandler;
    private final SystemHandler systemHandler;
    private final WeatherHandler weatherHandler;
    private final NotifyHandler notifyHandler;
    private final DefaultHandler defaultHandler;
    private final CurrencyHandler currencyHandler;
    private final CallbackWeatherHandler callBackWeatherHandler;

    @Override
    public void run() {
        log.info("[STARTED] MsgReciever.  Bot class: " + bot);
        while (true) {
            for (Object object = receiveQueue.poll(); object != null; object = receiveQueue.poll()) {
                log.debug("New object for analyze in queue " + object.toString());
                analyze(object);
            }
            try {
                Thread.sleep(WAIT_FOR_NEW_MESSAGE_DELAY);
            } catch (InterruptedException e) {
                log.error("Catch interrupt. Exit", e);
                return;
            }
        }
    }

    private void analyze(Object object) {
        if (object instanceof Update update) {
            log.debug("Update recieved: " + update.toString());
            analyzeForUpdateType(update);
        } else log.warn("Cant operate type of object: " + object.toString());
    }

    private void analyzeForUpdateType(Update update) {
        Long chatId;
        ParsedCommand parsedCommand = new ParsedCommand(Command.NONE, "");
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            chatId = callbackQuery.getMessage().getChatId();
            parsedCommand = new ParsedCommand(Command.CALLBACK, "");
        } else if (update.getMessage().hasText()) {
            Message message = update.getMessage();
            chatId = message.getChatId();
            parsedCommand = parser.getParsedCommand(message.getText());
        } else {
//for work replace NONE to STICKER
            Message message = update.getMessage();
            chatId = message.getChatId();
            Sticker sticker = message.getSticker();
            if (sticker != null) {
                parsedCommand = new ParsedCommand(Command.NONE, sticker.getFileId());
            }
        }

        AbstractHandler handlerForCommand = getHandlerForCommand(parsedCommand.getCommand());
        String operationResult = handlerForCommand.operate(chatId.toString(), parsedCommand, update);

        if (!"".equals(operationResult)) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(operationResult);
            Bot.sendQueue.add(sendMessage);
        }
    }

    private AbstractHandler getHandlerForCommand(Command command) {

        if (command == null) {
            log.warn("Null command accepted. This is not good scenario.");
            return new DefaultHandler(bot);
        }

        switch (command) {
            case GREETING, NEWSECTION, VALERA, GIRLS, MINING, STICKER -> {
                log.info("Handler for command[" + command.toString() + "] is: " + systemHandler);
                return systemHandler;
            }
            case CURRENCY -> {
                log.info("Handler for command[" + command.toString() + "] is: " + systemHandler);
                return currencyHandler;
            }
            case ANEKDOTE -> {
                log.info("Handler for command[" + command.toString() + "] is: " + anekdoteHandler);
                return anekdoteHandler;
            }
            case WEATHER -> {
                log.info("Handler for command[" + command.toString() + "] is: " + weatherHandler);
                return weatherHandler;
            }
            case NOTIFY -> {
                log.info("Handler for command[" + command.toString() + "] is: " + notifyHandler);
                return notifyHandler;
            }
            case CALLBACK -> {
                log.info("Handler for command[" + command.toString() + "] is: " + callBackWeatherHandler);
                return callBackWeatherHandler;
            }
            case TEXT_CONTAIN_EMOJI -> {
                EmojiHandler emojiHandler = new EmojiHandler(bot);
                log.info("Handler for command[" + command.toString() + "] is: " + emojiHandler);
                return emojiHandler;
            }
            default -> {
                log.info("Handler for command[" + command.toString() + "] not Set. Return DefaultHandler");
                return defaultHandler;
            }
        }
    }
}
