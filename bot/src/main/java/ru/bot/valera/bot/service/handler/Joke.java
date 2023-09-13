package ru.bot.valera.bot.service.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.model.persist.Anecdote;
import ru.bot.valera.bot.model.persist.Status;
import ru.bot.valera.bot.repository.AnecdoteRepository;
import ru.bot.valera.bot.repository.StatusRepository;
import ru.bot.valera.bot.to.UpdateTO;

import java.util.Set;

import static ru.bot.valera.bot.util.JokeUtil.getRandomAnekdoteId;
import static ru.bot.valera.bot.util.JokeUtil.getRandomStatusId;
import static ru.bot.valera.bot.util.WordsUtil.STATUS_CAPTION;

@Component
@RequiredArgsConstructor
public class Joke extends AbstractContent {

    private final StatusRepository statusRepository;
    private final AnecdoteRepository anecdoteRepository;

    @Override
    public Content handle(UpdateTO updateTO) {
        String text = "";
        int messageId = updateTO.getMessageId();
        switch (updateTO.getCommand()) {
            case ANECDOTE -> {
                text = anecdoteRepository.findById(getRandomAnekdoteId()).orElse(new Anecdote("" )).getAnecdote();
                if (messageId == 0) {
                    text = "*Анекдот:* \n\n" + text;
                }
            }
            case STATUSES -> {
                text = statusRepository.findById(getRandomStatusId()).orElse(new Status("" )).getStatus();
                if (messageId == 0) {
                    text = getRandomElementFromList(STATUS_CAPTION) + text;
                }
            }
        }
        return getContent(text, updateTO);
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(Command.ANECDOTE, Command.STATUSES);
    }
}
