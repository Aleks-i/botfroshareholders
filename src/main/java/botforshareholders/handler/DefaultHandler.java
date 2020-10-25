package botforshareholders.handler;

import botforshareholders.bot.Bot;
import botforshareholders.command.ParsedCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class DefaultHandler extends AbstractHandler {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultHandler.class);

    public DefaultHandler(Bot bot) {
        super(bot);
    }

    @Override
    public String operate(String chatId, ParsedCommand parsedCommand, Update update) {
        return "";
    }
}
