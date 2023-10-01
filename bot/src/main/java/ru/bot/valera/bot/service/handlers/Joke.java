package ru.bot.valera.bot.service.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.model.persist.Anecdote;
import ru.bot.valera.bot.model.persist.Status;
import ru.bot.valera.bot.repository.AnecdoteRepository;
import ru.bot.valera.bot.repository.StatusRepository;
import ru.bot.valera.bot.to.UpdateTo;

import java.util.Set;

import static ru.bot.valera.bot.model.SourceMessageType.TASK;
import static ru.bot.valera.bot.util.JokeUtil.getRandomAnekdoteId;
import static ru.bot.valera.bot.util.JokeUtil.getRandomStatusId;
import static ru.bot.valera.bot.util.WordsUtil.ANECDOTE_CAPTION;
import static ru.bot.valera.bot.util.WordsUtil.STATUS_CAPTION;

@Component
@RequiredArgsConstructor
public class Joke extends AbstractContent {

    private final StatusRepository statusRepository;
    private final AnecdoteRepository anecdoteRepository;

    @Override
    public Content handle(UpdateTo updateTo) {
        String text = "";
        switch (updateTo.getCommand()) {
            case ANECDOTE -> {
                text = anecdoteRepository.findById(getRandomAnekdoteId()).orElse(new Anecdote("")).getAnecdote();
                if (updateTo.getSourceMessageType() == TASK) {
                    text = getRandomElementFromList(ANECDOTE_CAPTION) + text;
                }
            }
            case STATUSES -> {
                text = statusRepository.findById(getRandomStatusId()).orElse(new Status("")).getStatus();
                if (updateTo.getSourceMessageType() == TASK) {
                    text = getRandomElementFromList(STATUS_CAPTION) + text;
                }
            }
        }
        return getContent(text, updateTo);
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(Command.ANECDOTE, Command.STATUSES);
    }
}
