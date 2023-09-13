package ru.bot.valera.bot.model.weather;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Root {

    String name;
    LocalNames local_names;
    double lat;
    double lon;
    String country;
    String state;

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class LocalNames {
        String ru;
    }
}
