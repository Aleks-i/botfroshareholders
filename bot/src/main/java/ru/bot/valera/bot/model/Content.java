package ru.bot.valera.bot.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor
public class Content {
    private final PartialBotApiMethod<? extends Serializable> content;
    private final Command command;

    @Override
    public String toString() {
        return "Content{" +
                "content=" + content +
                ", command=" + command +
                '}';
    }
}
