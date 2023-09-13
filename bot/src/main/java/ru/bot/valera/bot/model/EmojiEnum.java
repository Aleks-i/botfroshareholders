package ru.bot.valera.bot.model;

import lombok.Getter;

@Getter
public enum EmojiEnum {

    ENABLE("white_check_mark" ),
    DISABLE("x" ),
    RU("ru" ),
    MONEY("money_with_wings" );

    private final String title;

    EmojiEnum(String title) {
        this.title = title;
    }
}
