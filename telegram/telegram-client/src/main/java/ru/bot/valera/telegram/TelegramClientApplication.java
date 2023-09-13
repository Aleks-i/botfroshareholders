package ru.bot.valera.telegram;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.bot.valera.telegram.client.TdApi;
import ru.bot.valera.telegram.client.TelegramClient;
import ru.bot.valera.telegram.client.updates.ClientAuthorizationState;

import java.util.concurrent.TimeUnit;

@Slf4j
@EnableScheduling
@SpringBootApplication
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelegramClientApplication.class, args);
    }

    final TelegramClient telegramClient;
    final ClientAuthorizationState authorizationState;

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            while (!authorizationState.haveAuthorization()) {
                /*wait for authorization*/
                TimeUnit.MILLISECONDS.sleep(2_000);
            }
            TdApi.LoadChats loadChatsQuery = new TdApi.LoadChats(new TdApi.ChatListMain(), 100);
            telegramClient.sendWithCallback(loadChatsQuery, this::loadChatsHandler);
        };
    }

    public void loadChatsHandler(TdApi.Object object) {
        // https://core.telegram.org/tdlib/docs/classtd_1_1td__api_1_1load_chats.html
        // Returns a 404 error if all chats have been loaded.
        if (object instanceof TdApi.Ok) {
            TdApi.LoadChats loadChatsQuery = new TdApi.LoadChats(new TdApi.ChatListMain(), 100);
            telegramClient.sendWithCallback(loadChatsQuery, this::loadChatsHandler);
        } else {
            log.info("Chats loaded." );
        }
    }

}
