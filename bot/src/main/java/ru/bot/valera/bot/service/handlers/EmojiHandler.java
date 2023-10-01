package ru.bot.valera.bot.service.handlers;

import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.to.UpdateTo;

import java.util.HashSet;
import java.util.Set;

import static ru.bot.valera.bot.model.EmojiEnum.DISABLE;
import static ru.bot.valera.bot.model.EmojiEnum.ENABLE;

@Slf4j
@Component
public class EmojiHandler extends AbstractContent {

    @Override
    public Content handle(UpdateTo updateTo) {
        String message = "";

        StringBuilder result = new StringBuilder();
        Set<String> emojisInTextUnique = new HashSet<>(EmojiParser.extractEmojis(updateTo.getMessage().getText()));
        if (emojisInTextUnique.size() > 0) {
            result.append("Parsed emojis from message:").append("\n");
        }

        for (String emojiUnicode : emojisInTextUnique) {
            Emoji byUnicode = EmojiManager.getByUnicode(emojiUnicode);
            log.debug(byUnicode.toString());
            String emoji = byUnicode.getUnicode() + " " +
                    byUnicode.getAliases() +
                    "\n " + byUnicode.getDescription();
            result.append(emoji).append("\n");
        }
        result.append("\n")
                .append(EmojiManager.getForAlias(ENABLE.getTitle()).getUnicode()).append("\n")
                .append(EmojiManager.getForAlias(DISABLE.getTitle()).getUnicode());
        message = result.toString();
        return getContent(message, updateTo);
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(Command.TEXT_CONTAIN_EMOJI);
    }
}
