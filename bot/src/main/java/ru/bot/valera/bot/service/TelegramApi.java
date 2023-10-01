package ru.bot.valera.bot.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.TelegramBotConfig;
import ru.bot.valera.bot.model.telegram.api.ChatMember;
import ru.bot.valera.bot.model.telegram.api.ChatMembers;
import ru.bot.valera.bot.util.JsonUtil;

import java.util.stream.Collectors;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramApi {

    static final String CHAT_MEMBER_STATUS_RESTRICTED = "restricted";
    static final String CHAT_MEMBER_STATUS_LEFT = "left";
    static final String CHAT_MEMBER_STATUS_KICKED = "kicked";
    final String url;

    public TelegramApi(TelegramBotConfig telegramBotConfig) {
        this.url = "https://api.telegram.org/bot" + telegramBotConfig.getBotToken();
    }

    public ChatMembers getAdmins(long chatId) {
        return JsonUtil.readValue(url + "/getChatAdministrators?chat_id="
                + chatId, ChatMembers.class);
    }

    public ChatMember getChatMember(long chatId, long userId) {
        return JsonUtil.readValue(url + "/getChatMember?chat_id="
                + chatId + "&user_id=" + userId, ChatMember.class);
    }

    public boolean isGroupMember(long chatId, long userId) {
        ChatMember chatMember = getChatMember(chatId, userId);
        String chatMemberStatus = chatMember.getResult().getStatus();
        return !chatMemberStatus.equals(CHAT_MEMBER_STATUS_RESTRICTED)
                && !chatMemberStatus.equals(CHAT_MEMBER_STATUS_KICKED)
                && !chatMemberStatus.equals(CHAT_MEMBER_STATUS_LEFT);
    }

    public boolean isGroupAdmin(long chatId, long userIdFrom) {
        return getAdmins(chatId).getResult().stream()
                .map(chm -> chm.getUser().getId())
                .collect(Collectors.toSet())
                .contains(userIdFrom);
    }
}
