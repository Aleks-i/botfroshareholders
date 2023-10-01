package ru.bot.valera.bot.model.telegram.api;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatMembers {

    boolean ok;
    List<Result> result;

    @Data
    public static class Result {
        User user;
        String status;
        boolean can_be_edited;
        boolean can_manage_chat;
        boolean can_change_info;
        boolean can_delete_messages;
        boolean can_invite_users;
        boolean can_restrict_members;
        boolean can_pin_messages;
        boolean can_manage_topics;
        boolean can_promote_members;
        boolean can_manage_video_chats;
        boolean is_anonymous;
        boolean can_manage_voice_chats;
        String custom_title;
    }

    @Data
    public static class User {
        public long id;
        public boolean is_bot;
        public String first_name;
        public String username;
        public String language_code;
    }
}
