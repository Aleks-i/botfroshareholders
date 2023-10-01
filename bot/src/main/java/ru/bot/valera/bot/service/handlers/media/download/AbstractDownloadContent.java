package ru.bot.valera.bot.service.handlers.media.download;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.bot.valera.bot.bot.Bot;
import ru.bot.valera.bot.model.telegram.api.FilePath;
import ru.bot.valera.bot.service.handlers.media.AbstractMediaContent;
import ru.bot.valera.bot.util.JsonUtil;

import java.io.File;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class AbstractDownloadContent extends AbstractMediaContent {

    @Autowired
    //TODO delete autowired
    final Bot bot;
    final String baseUrl;

    protected AbstractDownloadContent(Bot bot) {
        this.bot = bot;
        this.baseUrl = bot.getBaseUrl() + "getFile?file_id=";
    }

    public File downloadFile(String fileId, String mimeType) {
        try {
            FilePath filePath = JsonUtil.readValue(baseUrl + fileId, FilePath.class);

            if (filePath != null && filePath.getResult() != null) {

                GetFile getFile = new GetFile(filePath.getResult().getFile_path());
                File mediaFile = bot.downloadFile(getFile.getFileId());

                File fileRename = new File(getFileName(mimeType));
                if (mediaFile.renameTo(fileRename)) {
                    return fileRename;
                } else {
                    log.warn("failed to rename mediaFile {} to {}", mediaFile.getName(), fileRename.getName());
                    return mediaFile;
                }
            }
        } catch (TelegramApiException e) {
            log.warn("file download error, message: {}", e.getMessage());
        }
        return null;
    }
}
