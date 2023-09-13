package ru.bot.valera.bot.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.bot.valera.bot.model.persist.chat.Chat;

@Repository
public interface ChatRepository extends ListCrudRepository<Chat, Long> {
}
