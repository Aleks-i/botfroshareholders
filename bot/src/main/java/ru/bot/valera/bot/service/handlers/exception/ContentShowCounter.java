package ru.bot.valera.bot.service.handlers.exception;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContentShowCounter {
    int count;
    boolean isShowNotify;

    public ContentShowCounter incrementCount() {
        count++;
        return this;
    }
}
