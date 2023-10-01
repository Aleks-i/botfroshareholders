package ru.bot.valera.bot.service.handlers.settings;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.model.persist.chat.Chat;
import ru.bot.valera.bot.model.persist.chat.ChatType;
import ru.bot.valera.bot.model.telegram.api.ChatMember;
import ru.bot.valera.bot.repository.ChatRepository;
import ru.bot.valera.bot.service.TelegramApi;
import ru.bot.valera.bot.service.handlers.AbstractContent;
import ru.bot.valera.bot.service.keyboards.CallBackSettingsKeyboard;
import ru.bot.valera.bot.to.UpdateTo;

import java.util.Map;
import java.util.Set;

import static ru.bot.valera.bot.bot.Bot.CHAT_STORAGE;
import static ru.bot.valera.bot.model.persist.chat.ChatType.PRIVATE;
import static ru.bot.valera.bot.util.ExceptionUtil.NON_ADMIN_SETTINGS_MESSAGE;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CallBackSettings extends AbstractContent {

    final ChatRepository chatRepository;
    final CallBackSettingsKeyboard callBackSettingsKeyboard;
    final TelegramApi telegramApi;

    @Override
    public Content handle(UpdateTo updateTo) {
        Chat chat = CHAT_STORAGE.get(updateTo.getChatId());
        ChatType chatType = chat.getChatType();

        if (chatType == PRIVATE) {
            return changeSettings(chat, updateTo);
        } else if (telegramApi.isGroupAdmin(chat.getChatId(), updateTo.getUserIdFrom())) {
            return changeSettings(chat, updateTo);
        } else {
            ChatMember chatMember = telegramApi.getChatMember(chat.getChatId(), updateTo.getUserIdFrom());
            updateTo.setCommand(Command.EXCEPTION_HANDLER);

            return getContent(getMessage(chatMember), updateTo);
        }
    }

    private Content changeSettings(Chat chat, UpdateTo updateTO) {
        Map<Command, Boolean> mailerMap = chat.getMailerMap();
        Command command = Command.valueOf(updateTO.getCallBackData()[0]);

        Boolean flag = !mailerMap.get(command);
        mailerMap.put(command, flag);
        chatRepository.save(chat);
        return getContent(updateTO, callBackSettingsKeyboard);
    }

    private String getMessage(ChatMember chatMember) {
        if (chatMember != null && chatMember.getResult() != null) {
            return "*" + chatMember.getResult().getUser().getUsername() + ":* "
                    + NON_ADMIN_SETTINGS_MESSAGE;
        }
        return NON_ADMIN_SETTINGS_MESSAGE;
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(Command.CALLBACK_SETTINGS);
    }
}
