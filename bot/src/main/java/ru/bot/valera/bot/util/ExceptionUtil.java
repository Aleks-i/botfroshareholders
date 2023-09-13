package ru.bot.valera.bot.util;

import lombok.experimental.UtilityClass;

import static ru.bot.valera.bot.AppConfig.COUNT_CONTENT_IN_DAY;

@UtilityClass
public class ExceptionUtil {
    public static final String COUNTER_EXCEPTION_MESSAGE = "Извини дорогой, но в тестовой версии робота, доступно только "
            + COUNT_CONTENT_IN_DAY + " запроса, на каждую категорию, для каждого отдельного чата.";

    public static final String NON_ADMIN_SETTINGS_MESSAGE = "Извини дорогой, но в супергруппах и группах данная опция доступна только администраторам.";
}
