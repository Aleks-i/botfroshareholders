package ru.bot.valera.bot.repository;


import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.bot.valera.bot.model.persist.Status;

@Repository
public interface StatusRepository extends ListCrudRepository<Status, Long> {
}
