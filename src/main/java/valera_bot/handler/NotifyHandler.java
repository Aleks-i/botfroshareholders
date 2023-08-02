package valera_bot.handler;

import valera_bot.ability.Notify;
import valera_bot.bot.Bot;
import valera_bot.command.ParsedCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class NotifyHandler extends AbstractHandler {
    private static final Logger LOG = LoggerFactory.getLogger(NotifyHandler.class);
    private final int MILLISEC_IN_SEC = 1000;
    private String WRONG_INPUT_MESSAGE = "Wrong input. Time must be specified as an integer greater than 0";

    public NotifyHandler(Bot bot) {
        super(bot);
    }

    @Override
    public String operate(String chatId, ParsedCommand parsedCommand, Update update) {
        String text = parsedCommand.getText();
        if ("".equals(text))
            return "You must specify the delay time. Like this:\n" +
                    "/notify 30";
        long timeInSec;
        try {
            timeInSec = Long.parseLong(text.trim());
        } catch (NumberFormatException e) {
            return WRONG_INPUT_MESSAGE;
        }
        if (timeInSec > 0) {
            Thread thread = new Thread(new Notify(bot,timeInSec * MILLISEC_IN_SEC, chatId));
            thread.start();
        } else return WRONG_INPUT_MESSAGE;
        return "";
    }
}
