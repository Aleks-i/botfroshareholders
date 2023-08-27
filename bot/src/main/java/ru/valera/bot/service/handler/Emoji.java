package ru.valera.bot.service.handler;

import com.vdurmont.emoji.EmojiManager;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.valera.bot.model.Content;
import ru.valera.bot.to.MessageType;
import ru.valera.bot.to.UpdateTO;
import ru.valera.bot.util.MessageUtil;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class Emoji implements Handler {

    @Override
    public Content handle(UpdateTO updateTO) {
        SendMessage sendMessage = new SendMessage();
        Long chatId = updateTO.getChatId();
        StringBuilder result = new StringBuilder();
        Set<String> emojisInTextUnique = new HashSet<>(EmojiParser.extractEmojis(String.valueOf(chatId)));
        if (emojisInTextUnique.size() > 0) {
            result.append("Parsed emojis from message:").append("\n");
        }
        for (String emojiUnicode : emojisInTextUnique) {
            com.vdurmont.emoji.Emoji byUnicode = EmojiManager.getByUnicode(emojiUnicode);
            log.debug(byUnicode.toString());
            String emoji = byUnicode.getUnicode() + " " +
                    byUnicode.getAliases() +
                    " " + byUnicode.getDescription();
            result.append(emoji).append("\n");
        }
        sendMessage.setText(result.toString());
        return new Content(MessageUtil.setParamMessage(sendMessage, updateTO), updateTO.getMessageType());
    }

    @Override
    public Set<MessageType> getMessageType() {
        return Set.of(MessageType.TEXT_CONTAIN_EMOJI);
    }
}
