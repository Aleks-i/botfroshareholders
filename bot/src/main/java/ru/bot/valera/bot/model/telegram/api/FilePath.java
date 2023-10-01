package ru.bot.valera.bot.model.telegram.api;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilePath {

    boolean ok;
    Result result;

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Result{
        String file_id;
        int file_size;
        String file_path;
    }

}
