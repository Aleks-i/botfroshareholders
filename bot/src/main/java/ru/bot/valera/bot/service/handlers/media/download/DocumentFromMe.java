package ru.bot.valera.bot.service.handlers.media.download;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.service.handlers.media.AbstractMediaContent;
import ru.bot.valera.bot.to.UpdateTo;

import java.util.Set;

import static ru.bot.valera.bot.model.Command.*;
import static ru.bot.valera.bot.service.handlers.media.MimeType.GIF;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DocumentFromMe extends AbstractMediaContent {

    final FromMeAnimation fromMeAnimation;

    @Override
    public Content handle(UpdateTo updateTo) {
        if (updateTo.getMessage().getDocument().getMimeType().equals(GIF.getType())) {
            updateTo.setCommand(TITS_GIF);
            return fromMeAnimation.handle(updateTo);
        }
        return new Content(new SendMessage(), NONE);
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(DOCUMENT_FROM_ME);
    }
}
