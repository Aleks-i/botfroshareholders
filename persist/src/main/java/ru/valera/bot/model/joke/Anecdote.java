package ru.valera.bot.model.joke;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.valera.bot.model.AbstractEntity;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "anecdotes")
public class Anecdote extends AbstractEntity {
    //    private int cat;
    private String anecdote;
}

