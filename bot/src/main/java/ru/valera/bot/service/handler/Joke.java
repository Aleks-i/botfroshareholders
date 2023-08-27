package ru.valera.bot.service.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.valera.bot.model.Content;
import ru.valera.bot.model.joke.Anecdote;
import ru.valera.bot.model.joke.Status;
import ru.valera.bot.repository.AnecdoteRepository;
import ru.valera.bot.repository.StatusRepository;
import ru.valera.bot.to.MessageType;
import ru.valera.bot.to.UpdateTO;

import java.util.Set;

import static ru.valera.bot.util.JokeUtil.getRandomAnekdoteId;
import static ru.valera.bot.util.JokeUtil.getRandomStatusId;
import static ru.valera.bot.util.MessageUtil.getContent;

@Component
@RequiredArgsConstructor
public class Joke implements Handler {

    private final StatusRepository statusRepository;
    private final AnecdoteRepository anecdoteRepository;

    @Override
    public Content handle(UpdateTO updateTO) {
        String text = "";
        switch (updateTO.getMessageType()) {
            case ANECDOTE ->
                    text = anecdoteRepository.findById(getRandomAnekdoteId()).orElse(new Anecdote("")).getAnecdote();
            case STATUSES_VK ->
                    text = statusRepository.findById(getRandomStatusId()).orElse(new Status("")).getStatus();
        }
        return getContent(text, updateTO);
    }

    @Override
    public Set<MessageType> getMessageType() {
        return Set.of(MessageType.ANECDOTE, MessageType.STATUSES_VK);
    }
}
