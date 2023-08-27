package ru.valera.bot.model.joke;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.valera.bot.model.AbstractEntity;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "statuses")
public class Status extends AbstractEntity {
    private String status;
}
