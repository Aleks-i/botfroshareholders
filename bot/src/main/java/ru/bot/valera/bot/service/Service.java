package ru.bot.valera.bot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.bot.valera.bot.exception.MessageCounterException;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.model.persist.chat.Chat;
import ru.bot.valera.bot.service.handler.Handler;
import ru.bot.valera.bot.service.handler.exception.ContentShowCounter;
import ru.bot.valera.bot.to.UpdateTO;

import java.util.Map;

import static ru.bot.valera.bot.AppConfig.COUNT_CONTENT_IN_DAY;
import static ru.bot.valera.bot.bot.Bot.CHATS_CONTENT_COUNTER;
import static ru.bot.valera.bot.bot.Bot.CHAT_STORAGE;
import static ru.bot.valera.bot.bot.MessageSender.sendQueue;
import static ru.bot.valera.bot.util.ExceptionUtil.COUNTER_EXCEPTION_MESSAGE;

@Slf4j
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class Service {

    private final Map<Command, Handler> handlerMap;

    @ExceptionHandler(MessageCounterException.class)
    public void process(UpdateTO updateTo) {
        log.info("service get to command {}", updateTo.getCommand());
        Command command = updateTo.getCommand();

        Content content;
        Chat chat;

        if (command == Command.START) {
            content = handlerMap.get(command).handle(updateTo);
            sendQueue.add(content);
        } else if (command != Command.NONE && (chat = CHAT_STORAGE.get(updateTo.getChatId())) != null
                && chat.getMailerMap().get(command)) {
            Map<Command, ContentShowCounter> contentCounters = CHATS_CONTENT_COUNTER.get(updateTo.getChatId());
            if (contentCounters.get(command) != null) {
                if (contentCounters.get(command).getCount() == COUNT_CONTENT_IN_DAY) {
                    Map<Command, ContentShowCounter> showCounterMap = CHATS_CONTENT_COUNTER.get(chat.getChatId());
                    ContentShowCounter counter = showCounterMap.get(command);
                    if (!counter.isShow()) {
                        command = Command.EXCEPTION_HANDLER;
                        updateTo.setCommand(command);
                        updateTo.setErrorMessage(COUNTER_EXCEPTION_MESSAGE);
                        counter.setShow(!counter.isShow());

                        content = handlerMap.get(command).handle(updateTo);
                        log.info("sendQueue add content {}", content);

                        sendQueue.add(content);
                    }
                } else {
                    contentCounters.computeIfPresent(command, (k, v) -> v.incrementCount());

                    content = handlerMap.get(command).handle(updateTo);
                    log.info("sendQueue add content {}", content);

                    sendQueue.add(handlerMap.get(command).handle(updateTo));
                }
            } else {
                content = handlerMap.get(command).handle(updateTo);
                log.info("sendQueue add content {}", content);

                sendQueue.add(content);
            }
        }
    }
}
