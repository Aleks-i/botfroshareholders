package botforshareholders.handler;

import botforshareholders.bot.Bot;
import botforshareholders.command.ParsedCommand;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class AbstractHandler {
    Bot bot;

    public AbstractHandler(Bot bot) {
        this.bot = bot;
    }

    public abstract String operate(String chatId, ParsedCommand parsedCommand, Update update);
}
