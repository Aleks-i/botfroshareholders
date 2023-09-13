package ru.bot.valera.bot.service.handler.exception;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContentShowCounter {
    int count;
    boolean isShow;

    public ContentShowCounter incrementCount() {
        count++;
        return this;
    }
}
