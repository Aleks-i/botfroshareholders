package ru.bot.valera.bot.model.persist;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "anecdotes" )
public class Anecdote extends AbstractBaseEntity {
    //    private int cat;
    private String anecdote;
}

