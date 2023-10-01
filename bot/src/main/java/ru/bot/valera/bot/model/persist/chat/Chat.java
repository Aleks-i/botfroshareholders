package ru.bot.valera.bot.model.persist.chat;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.persist.AbstractBaseEntity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Table(name = "chats" )
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Chat extends AbstractBaseEntity {

    long chatId;

    String userName;
    String firstName;
    String lastName;

    @Enumerated(EnumType.STRING)
    ChatType chatType;

    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyEnumerated(value = EnumType.STRING)
    @CollectionTable(name = "mailer_type",
            joinColumns = {@JoinColumn(name = "chat_id", referencedColumnName = "id" )})
    @MapKeyColumn(name = "type" )
    @Column(name = "is_active" )
    final Map<Command, Boolean> mailerMap = new HashMap<>();

    public Chat(long chatId, String userName, ChatType chatType, String firstName, String lastName) {
        this.chatId = chatId;
        this.userName = userName;
        this.chatType = chatType;
        this.firstName = firstName;
        this.lastName = lastName;
        Arrays.stream(Command.values())
                .forEach(mt -> mailerMap.put(mt, true));
    }
}
