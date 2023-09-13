package ru.bot.valera.bot.model.telegram;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatMember {

    boolean ok;
    Result result;

    @Data
    public static class Result {
        User user;
        String status;

        @Data
        public static class User {
            int id;
            boolean is_bot;
            String first_name;
            String last_name;
            String username;
            String language_code;
        }
    }
}
