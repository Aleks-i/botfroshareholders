package ru.bot.valera.bot.service.handler.settings;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.TelegramBotConfig;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.model.persist.chat.Chat;
import ru.bot.valera.bot.model.persist.chat.ChatType;
import ru.bot.valera.bot.model.telegram.ChatMember;
import ru.bot.valera.bot.model.telegram.ChatMembers;
import ru.bot.valera.bot.repository.ChatRepository;
import ru.bot.valera.bot.service.handler.AbstractContent;
import ru.bot.valera.bot.service.keyboards.CallBackSettingsKeyboard;
import ru.bot.valera.bot.to.UpdateTO;
import ru.bot.valera.bot.util.JsonUtil;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.bot.valera.bot.bot.Bot.CHAT_STORAGE;
import static ru.bot.valera.bot.util.ExceptionUtil.NON_ADMIN_SETTINGS_MESSAGE;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CallBackSettings extends AbstractContent {

    final ChatRepository chatRepository;
    final CallBackSettingsKeyboard callBackSettingsKeyboard;
    final String url;

    public CallBackSettings(ChatRepository chatRepository, CallBackSettingsKeyboard callBackSettingsKeyboard, TelegramBotConfig telegramBotConfig) {
        this.chatRepository = chatRepository;
        this.callBackSettingsKeyboard = callBackSettingsKeyboard;
        this.url = "https://api.telegram.org/bot" + telegramBotConfig.getBotToken();
    }

    @Override
    public Content handle(UpdateTO updateTO) {
        Chat chat = CHAT_STORAGE.get(updateTO.getChatId());
        ChatType chatType = chat.getChatType();
        ChatMembers adminChatMembers = JsonUtil.readValue(url + "/getChatAdministrators?chat_id="
                + chat.getChatId(), ChatMembers.class);

        if (chatType == ChatType.PRIVATE) {
            return changeSettings(chat, updateTO);
        } else if (adminChatMembers != null && getChatAdminsIds(adminChatMembers).contains(updateTO.getUserIdFrom())) {
            return changeSettings(chat, updateTO);
        } else {
            ChatMember chatMember = JsonUtil.readValue(url + "/getChatMember?chat_id="
                    + chat.getChatId() + "&user_id=" + updateTO.getUserIdFrom(), ChatMember.class);

            updateTO.setCommand(Command.EXCEPTION_HANDLER);

            return getContent(getMessage(chatMember), updateTO);
        }
    }

    private Content changeSettings(Chat chat, UpdateTO updateTO) {
        Map<Command, Boolean> mailerMap = chat.getMailerMap();
        Command command = Command.valueOf(updateTO.getCallBackData()[0]);

        Boolean flag = !mailerMap.get(command);
        mailerMap.put(command, flag);
        chatRepository.save(chat);
        return getContent(updateTO, callBackSettingsKeyboard);
    }

    private Set<Long> getChatAdminsIds(ChatMembers chatMembers) {
        return chatMembers.getResult().stream()
                .map(chm -> chm.getUser().getId())
                .collect(Collectors.toSet());
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
