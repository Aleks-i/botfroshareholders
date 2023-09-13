package ru.bot.valera.telegram.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bot.valera.telegram.client.TdApi;
import ru.bot.valera.telegram.client.TelegramClient;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/info", produces = MediaType.APPLICATION_JSON_VALUE)
public class InfoController {

    private final TelegramClient telegramClient;

    @GetMapping("/getMe" )
    public TdApi.User getMe() {
        return telegramClient.sendSync(new TdApi.GetMe(), TdApi.User.class);
    }

    @GetMapping("/chatTitles" )
    public List<String> getMyChats() {
        TdApi.Chats chats = telegramClient.sendSync(new TdApi.GetChats(new TdApi.ChatListMain(), 100), TdApi.Chats.class);
        return Arrays.stream(chats.chatIds)
                .mapToObj(chatId -> {
                    TdApi.Chat chat = telegramClient.sendSync(new TdApi.GetChat(chatId), TdApi.Chat.class);
                    return chat.title;
                }).toList();
    }

    @GetMapping("/sendHello" )
    public void helloToYourself() {
        telegramClient.sendAsync(new TdApi.GetMe(), TdApi.User.class)
                .thenApply(user -> user.usernames.activeUsernames[0])
                .thenApply(username -> telegramClient.sendAsync(new TdApi.SearchChats(username, 1), TdApi.Chats.class))
                .thenCompose(chatsFuture ->
                        chatsFuture.thenApply(chats -> chats.chatIds[0]))
                .thenApply(chatId -> telegramClient.sendAsync(sendMessageQuery(chatId)));
    }

    private TdApi.SendMessage sendMessageQuery(Long chatId) {
        var content = new TdApi.InputMessageText();
        var formattedText = new TdApi.FormattedText();
        formattedText.text = "Hello!";
        content.text = formattedText;
        return new TdApi.SendMessage(chatId, 0, null, null, null, content);
    }

}
