package ru.bot.valera.bot.model.persist.chat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.persist.AbstractBaseEntity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Table(name = "chats" )
@NoArgsConstructor
public class Chat extends AbstractBaseEntity {

    @Column(name = "chat_id" )
    private long chatId;

    @Column(name = "type_chat" )
    @Enumerated(EnumType.STRING)
    private ChatType chatType;

    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyEnumerated(value = EnumType.STRING)
    @CollectionTable(name = "mailer_type",
            joinColumns = {@JoinColumn(name = "chat_id", referencedColumnName = "id" )})
    @MapKeyColumn(name = "type" )
    @Column(name = "is_active" )
    private final Map<Command, Boolean> mailerMap = new HashMap<>();

    public Chat(long chatId, ChatType chatType) {
        this.chatId = chatId;
        this.chatType = chatType;
        Arrays.stream(Command.values())
                .forEach(mt -> mailerMap.put(mt, true));
    }
}
