package ru.valera.bot.model.chat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.valera.bot.model.AbstractEntity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Table(name = "chats")
@NoArgsConstructor
public class Chat extends AbstractEntity {

    @Column(name = "chat_id")
    private long chatId;

    @Column(name = "type_chat")
    @Enumerated(EnumType.STRING)
    private ChatType chatType;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "sender_type",
            joinColumns = {@JoinColumn(name = "chat_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "type_chat")
    @Column(name = "is_active")
    private final Map<MailerType, Boolean> senderMap = new HashMap<>();

    public Chat(long chatId, ChatType chatType) {
        this.chatId = chatId;
        this.chatType = chatType;
        Arrays.stream(MailerType.values())
                .forEach(st -> senderMap.put(st, false));
    }
}
