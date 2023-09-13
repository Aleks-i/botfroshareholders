package ru.bot.valera.bot.service.handler;

import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.to.UpdateTO;

import java.util.HashSet;
import java.util.Set;

import static ru.bot.valera.bot.model.EmojiEnum.DISABLE;
import static ru.bot.valera.bot.model.EmojiEnum.ENABLE;

@Slf4j
@Component
public class EmojiHandler extends AbstractContent {

    @Override
    public Content handle(UpdateTO updateTO) {
        String message = "";

        StringBuilder result = new StringBuilder();
        Set<String> emojisInTextUnique = new HashSet<>(EmojiParser.extractEmojis(updateTO.getMessage().getText()));
        if (emojisInTextUnique.size() > 0) {
            result.append("Parsed emojis from message:" ).append("\n" );
        }

        for (String emojiUnicode : emojisInTextUnique) {
            Emoji byUnicode = EmojiManager.getByUnicode(emojiUnicode);
            log.debug(byUnicode.toString());
            String emoji = byUnicode.getUnicode() + " " +
                    byUnicode.getAliases() +
                    "\n " + byUnicode.getDescription();
            result.append(emoji).append("\n" );
        }
        result.append("\n" )
                .append(EmojiManager.getForAlias(ENABLE.getTitle()).getUnicode()).append("\n" )
                .append(EmojiManager.getForAlias(DISABLE.getTitle()).getUnicode());
        message = result.toString();
        return getContent(message, updateTO);
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(Command.TEXT_CONTAIN_EMOJI);
    }
}
