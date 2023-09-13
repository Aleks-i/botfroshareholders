package ru.bot.valera.bot.model.persist;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "statuses" )
public class Status extends AbstractBaseEntity {
    private String status;
}
