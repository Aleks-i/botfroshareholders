package ru.valera.bot.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.valera.bot.model.joke.Status;

@Repository
public interface StatusRepository extends ListCrudRepository<Status, Long> {
}
