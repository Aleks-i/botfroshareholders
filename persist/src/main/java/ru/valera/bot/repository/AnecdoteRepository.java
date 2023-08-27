package ru.valera.bot.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.valera.bot.model.joke.Anecdote;

@Repository
public interface AnecdoteRepository extends ListCrudRepository<Anecdote, Long> {
}
