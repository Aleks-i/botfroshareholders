package ru.bot.valera.bot.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.SourceMessageType;
import ru.bot.valera.bot.model.persist.chat.Chat;
import ru.bot.valera.bot.service.handlers.Handler;
import ru.bot.valera.bot.service.handlers.exception.ContentShowCounter;
import ru.bot.valera.bot.to.UpdateTo;

import java.util.Map;

import static ru.bot.valera.bot.bot.Bot.*;
import static ru.bot.valera.bot.bot.MessageSender.sendQueue;
import static ru.bot.valera.bot.model.Command.NONE;
import static ru.bot.valera.bot.model.Command.START;
import static ru.bot.valera.bot.model.SourceMessageType.BOT;
import static ru.bot.valera.bot.model.SourceMessageType.TASK;
import static ru.bot.valera.bot.util.ExceptionUtil.COUNTER_EXCEPTION_MESSAGE;

@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class Service {

    private final Map<Command, Handler> handlerMap;

    public void process(UpdateTo updateTo) {
        SourceMessageType sourceMessageType = updateTo.getSourceMessageType();
        if (sourceMessageType == BOT) {
            processUpdate(updateTo);
        } else if (sourceMessageType == TASK) {
            processTasks(updateTo);
        }
    }

    public void processUpdate(UpdateTo updateTo) {
        log.info("service get updateTo, command: {}", updateTo.getCommand());
        Command command = updateTo.getCommand();

        Chat chat;
        if (command == START) {
            log.info("sendqueue add content for command {}", command);
            sendQueue.add(handlerMap.get(command).handle(updateTo));
        } else if ((chat = CHAT_STORAGE.get(updateTo.getChatId())) != null
                && command != NONE && chat.getMailerMap().get(command)) {

            long chatId = chat.getChatId();
            Map<Command, ContentShowCounter> contentCounters;
            if ((contentCounters = CHATS_CONTENT_COUNTER.get(chatId)).get(command) != null) {

                if (contentCounters.get(command).getCount() == COUNT_CONTENT_IN_DAY) {
                    Map<Command, ContentShowCounter> showCounterMap = CHATS_CONTENT_COUNTER.get(chatId);
                    ContentShowCounter counter = showCounterMap.get(command);

                    if (!counter.isShowNotify()) {
                        command = Command.EXCEPTION_HANDLER;
                        updateTo = new UpdateTo(BOT, command, chatId, updateTo.getMessageId());
                        updateTo.setErrorMessage(COUNTER_EXCEPTION_MESSAGE);
                        counter.setShowNotify(true);
                        log.info("send queue add content for command {}", command);
                        sendQueue.add(handlerMap.get(command).handle(updateTo));
                    }
                } else {
                    contentCounters.computeIfPresent(command, (k, v) -> v.incrementCount());
                    log.info("send queue add content for command {}", command);
                    sendQueue.add(handlerMap.get(command)
                            .handle(updateTo));
                }
            } else {
                log.info("send queue add content for command {}", command);
                sendQueue.add(handlerMap.get(command).handle(updateTo));
            }
        }
    }

    public void processTasks(UpdateTo updateTO) {
        Command command = updateTO.getCommand();
        log.info("service get updateto, command: {}", command);

        CHAT_STORAGE.entrySet().stream()
                .filter(es -> es.getValue().getMailerMap().get(command))
                .map(es -> handlerMap.get(command).handle(new UpdateTo(TASK, command, es.getKey())))
                .forEach(sendQueue::add);
        System.gc();
    }
}
