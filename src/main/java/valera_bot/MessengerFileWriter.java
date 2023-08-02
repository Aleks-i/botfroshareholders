package valera_bot;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessengerFileWriter {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private MessengerFileWriter() {
    }

    public static void writeMessageToFile(Message message) {
        try (FileWriter fileWriter = new FileWriter("E:\\temp\\bot.txt", StandardCharsets.UTF_8, true)) {
            if (message == null) {
                fileWriter.write("Без сообщения\n");
                fileWriter.flush();
            } else {
                String string = LocalDateTime.now().format(DATE_TIME_FORMATTER) + "   CatId: " + message.getChatId().toString() +
                        "; UserId: " + message.getFrom().getId() + "; UserName: " + message.getFrom().getUserName() +
                        "; LastName: " + message.getFrom().getLastName() + "; FirstName: " + message.getFrom().getFirstName() +
                        "; message: " + message.getText() + "\n";
                fileWriter.write(string);
                fileWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}