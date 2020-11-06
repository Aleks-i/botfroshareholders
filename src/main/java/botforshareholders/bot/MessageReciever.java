package botforshareholders.bot;

import botforshareholders.handler.CallbackWeatherHandler;
import botforshareholders.command.Command;
import botforshareholders.command.ParsedCommand;
import botforshareholders.command.Parser;
import botforshareholders.handler.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;

@Component
public class MessageReciever implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(MessageReciever.class);
    private final int WAIT_FOR_NEW_MESSAGE_DELAY = 1000;

    private Bot bot;
    private final Parser parser;
    private final AnekdoteHandler anekdoteHandler;
    private final SystemHandler systemHandler;
    private final WeatherHandler weatherHandler;
    private final NotifyHandler notifyHandler;
    private final DefaultHandler defaultHandler;
    private final CurrencyHandler currencyHandler;
    private final CallbackWeatherHandler callBackWeatherHandler;

    @Autowired
    public MessageReciever(Bot bot, Parser parser, AnekdoteHandler anekdoteHandler, SystemHandler systemHandler, WeatherHandler weatherHandler, NotifyHandler notifyHandler, DefaultHandler defaultHandler, CurrencyHandler currencyHandler, CallbackWeatherHandler callBackWeatherHandler) {
        this.bot = bot;
        this.parser = parser;
        this.anekdoteHandler = anekdoteHandler;
        this.systemHandler = systemHandler;
        this.weatherHandler = weatherHandler;
        this.notifyHandler = notifyHandler;
        this.defaultHandler = defaultHandler;
        this.currencyHandler = currencyHandler;
        this.callBackWeatherHandler = callBackWeatherHandler;
    }

    @Override
    public void run() {
        LOG.info("[STARTED] MsgReciever.  Bot class: " + bot);
        while (true) {
            for (Object object = Bot.receiveQueue.poll(); object != null; object = Bot.receiveQueue.poll()) {
                LOG.debug("New object for analyze in queue " + object.toString());
                analyze(object);
            }
            try {
                Thread.sleep(WAIT_FOR_NEW_MESSAGE_DELAY);
            } catch (InterruptedException e) {
                LOG.error("Catch interrupt. Exit", e);
                return;
            }
        }
    }

    private void analyze(Object object) {
        if (object instanceof Update) {
            Update update = (Update) object;
            LOG.debug("Update recieved: " + update.toString());
            analyzeForUpdateType(update);
        } else LOG.warn("Cant operate type of object: " + object.toString());
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
            LOG.warn("Null command accepted. This is not good scenario.");
            return new DefaultHandler(bot);
        }

        switch (command) {
            case GREETING, NEWSECTION, VALERA, GIRLS, MINING, STICKER -> {
                LOG.info("Handler for command[" + command.toString() + "] is: " + systemHandler);
                return systemHandler;
            }
            case CURRENCY -> {
                LOG.info("Handler for command[" + command.toString() + "] is: " + systemHandler);
                return currencyHandler;
            }
            case ANEKDOTE -> {
                LOG.info("Handler for command[" + command.toString() + "] is: " + anekdoteHandler);
                return anekdoteHandler;
            }
            case WEATHER -> {
                LOG.info("Handler for command[" + command.toString() + "] is: " + weatherHandler);
                return weatherHandler;
            }
            case NOTIFY -> {
                LOG.info("Handler for command[" + command.toString() + "] is: " + notifyHandler);
                return notifyHandler;
            }
            case CALLBACK -> {
                LOG.info("Handler for command[" + command.toString() + "] is: " + callBackWeatherHandler);
                return callBackWeatherHandler;
            }
            case TEXT_CONTAIN_EMOJI -> {
                EmojiHandler emojiHandler = new EmojiHandler(bot);
                LOG.info("Handler for command[" + command.toString() + "] is: " + emojiHandler);
                return emojiHandler;
            }
            default -> {
                LOG.info("Handler for command[" + command.toString() + "] not Set. Return DefaultHandler");
                return defaultHandler;
            }
        }
    }

    public Bot getBot() {
        return bot;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }
}
