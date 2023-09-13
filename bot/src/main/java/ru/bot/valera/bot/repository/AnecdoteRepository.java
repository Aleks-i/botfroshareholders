package ru.bot.valera.bot.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.bot.valera.bot.model.persist.Anecdote;

@Repository
public interface AnecdoteRepository extends ListCrudRepository<Anecdote, Long> {
}
