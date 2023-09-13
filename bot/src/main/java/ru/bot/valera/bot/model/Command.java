package ru.bot.valera.bot.model;

import lombok.Getter;

@Getter
public enum Command {
    NONE("" ), DEFAULT("" ),

    START("" ), END("" ), HELP("" ), EXCEPTION_HANDLER("" ), SETTINGS("" ),
    CALLBACK_SETTINGS("" ), EDIT_MESSAGE("" ), EDIT_WEATHER_KEYBOARD("" ),

    GREETING("Валеркин привет" ), VALERA("Обращения к Валере" ), MINING("Майнинг-меню" ), CALLBACK_MINING("Майнинг" ),
    NEW_USER("Новые участники" ), LEFT_USER("Ушедшие участники" ),
    CURRENCY("Криптовалюты" ), WEATHER("Погода - меню" ), CALLBACK_WEATHER("Погода" ), STICKER("" ),

    GIRLS("Девочки меню" ), TITS_GIF("Титько-гифки" ), TITS_VIDEO("Титько-видоcики" ), GIRLS_VIDEO("Красотки" ),
    MANS("Мужчины меню" ), MANS_VIDEO("Мужч Видосики" ), MANS_GIF("Мужч Гифки" ),

    STATUSES("Изречения" ), ANECDOTE("Анекдоты" ), HOROSCOPE("Гороскопы" ), TEXT_CONTAIN_EMOJI("" ),
    ROCK("Тот самый Рок" ),

    PAYMENT("" ),

    GOOD_MORNING("Утреннее" ), GOOD_NIGHT("Вечернее" ), FRIDAY("Пятничное" );

    private final String title;

    Command(String title) {
        this.title = title;
    }
}
