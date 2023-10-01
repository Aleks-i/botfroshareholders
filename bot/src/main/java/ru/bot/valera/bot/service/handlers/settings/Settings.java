package ru.bot.valera.bot.service.handlers.settings;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.service.handlers.AbstractContent;
import ru.bot.valera.bot.service.keyboards.CallBackSettingsKeyboard;
import ru.bot.valera.bot.to.UpdateTo;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class Settings extends AbstractContent {

    private final CallBackSettingsKeyboard callBackSettingsKeyboard;

    @Override
    public Content handle(UpdateTo updateTo) {
        return getContent("Включение/отключение кнопок основной клавиатуры и ежедневной рассылки " +
                        "сообщений(в группах доступна только администраторам): ",
                updateTo, callBackSettingsKeyboard);
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(Command.SETTINGS);
    }
}
