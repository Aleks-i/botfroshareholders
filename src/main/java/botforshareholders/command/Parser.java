package botforshareholders.command;

import java.util.Arrays;
import java.util.List;

import botforshareholders.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Parser {
    private static final Logger LOG = LoggerFactory.getLogger(Parser.class);
    private final String PREFIX_FOR_COMMAND = "/";

    private final String botName;

    public Parser(@Qualifier("beanBotUsername") String botName) {
        this.botName = botName;
    }

    public ParsedCommand getParsedCommand(String text) {
        ParsedCommand result = new ParsedCommand(Command.NONE, "");
        if (text != null) result.setText(text);

        if ("".equals(text)) return result;
        Pair commandAndText = getPairCommandAndText(text);
        Command command = commandAndText.getKey();
        if (isCommand(command)) {
            if (text != null && isCommandForMe(text)) {
                result.setText(text);
                result.setCommand(command);
            } else {
                result.setCommand(Command.NOTFORME);
                result.setText(text);
            }
        }
        return result;
    }

    private boolean isCommand(Command command) {
        for (Command commandFromENUM : Command.values()) {
            if (command.equals(Command.NONE)) return false;
            if (command.equals(commandFromENUM)) return true;
        }
        return false;
    }

    private boolean isCommandForMe(String text) {
        String DELIMITER_COMMAND_BOTNAME = "@";
        if (text.contains(DELIMITER_COMMAND_BOTNAME)) {
            String botNameForEqual = text.substring(text.indexOf(DELIMITER_COMMAND_BOTNAME) + 1);
            return botName.equals(botNameForEqual);
        }
        return true;
    }

    private Pair getPairCommandAndText(String text) {
        Pair commandText;
        List<String> words = getWordsFromMessage(text);

         if (words.contains("валера") || words.contains("валерий") || words.contains("валерчик") ||
                words.contains("валерьяныч") || words.contains("валерка") || words.contains("валер")) {
            commandText = new Pair(Command.VALERA, text);
        } else if (words.contains("что") && words.contains("нибудь") && words.contains("еще")) {
            commandText = new Pair(Command.NEWSECTION, text);
        } else if ((words.contains("курс") || words.contains("курсы")) && words.contains("валют")) {
            commandText = new Pair(Command.CURRENCY, text);
        } else if (words.contains("голые") && words.contains("бабы")) {
            commandText = new Pair(Command.GIRLS,text);
        } else if (words.contains("чо") && words.contains("майнить")) {
            commandText = new Pair(Command.MINING, text);
        } else if (words.contains("замори") && words.contains("анекдот")) {
            commandText = new Pair(Command.ANEKDOTE, text);
        } else if (words.contains("погода") || text.startsWith("/")) {
            commandText = new Pair(Command.WEATHER, text);
        } else if (words.contains("привет") || words.contains("здарово") || words.contains("здарова")) {
             commandText = new Pair(Command.GREETING, text);
         } else commandText = new Pair(Command.NONE, text);
        return commandText;
    }

    public static List<String> getWordsFromMessage(String string) {
        List<String> list = Arrays.asList(string.split("[., !?:;-]").clone());
        for (int i = 0; i < list.size(); i++) {
            list.set(i, list.get(i).toLowerCase());
        }
        return list;
    }
}
