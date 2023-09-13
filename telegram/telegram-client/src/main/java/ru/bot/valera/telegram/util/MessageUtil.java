package ru.bot.valera.telegram.util;

import lombok.experimental.UtilityClass;

import java.util.Set;

@UtilityClass
public class MessageUtil {
    public static final Set<String> HOLIDAYS_WORDS = Set.of(
            "#праздники"
    );

    public static final Set<String> HOROSCOPE_WORDS = Set.of(
            "♈️Овен:",
            "♉️Телец:",
            "♊️Близнецы:",
            "♋️Рак:",
            "♌️Лев:",
            "♍️Дева:",
            "♎️Весы:",
            "♏️Скорпион:",
            "♐️Стрелец:",
            "♑️Козерог:",
            "♒️Водолей:",
            "♓️Рыбы:"
    );
}
