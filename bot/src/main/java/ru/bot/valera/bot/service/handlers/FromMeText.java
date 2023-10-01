package ru.bot.valera.bot.service.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bot.valera.bot.TelegramBotConfig;
import ru.bot.valera.bot.bot.Bot;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.to.UpdateTo;

import java.util.Set;

import static ru.bot.valera.bot.model.Command.FROM_ME_TEXT;

@Slf4j
@Component
@RequiredArgsConstructor
public class FromMeText extends AbstractContent {

    final Bot bot;
    final TelegramBotConfig telegramBotConfig;

    @Override
    public Content handle(UpdateTo updateTo) {
        log.info("FromMeTextHandler");
        return getTextContent(updateTo);
    }

    protected Content getTextContent(UpdateTo updateTO) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramBotConfig.getMY_TELEGRAM_CHANNEL_ID());
        Message message = updateTO.getMessage();

        return getContent(message.getText(), updateTO);
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(FROM_ME_TEXT);
    }
}
