package ru.valera.bot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import ru.valera.bot.to.MessageType;

@Getter
@AllArgsConstructor
public class Content {
    private PartialBotApiMethod content;
    private MessageType messageType;

    @Override
    public String toString() {
        return "Content{" +
                "content=" + content +
                ", command=" + messageType +
                '}';
    }
}
