package ru.valera.bot.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.valera.bot.model.chat.Chat;

@Repository
public interface ChatRepository extends ListCrudRepository<Chat, Long> {
}
