package ru.bot.valera.bot.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.time.LocalDate;
import java.util.Objects;

import static ru.bot.valera.bot.service.handlers.media.MimeType.MP4;

@Slf4j
public class FIleRenamer {

    private static final String path = "E:/Programs/valerabot/content/video-mans";

    static volatile int counterContentName = 0;

    public static void main(String[] args) {
        File dir = new File(path);

        if (dir.isDirectory()) {
            for (final File f : Objects.requireNonNull(dir.listFiles())) {
                try {
                    File newfile = new File(path + "/" + getFileName());

                    if (f.renameTo(newfile)) {
                        log.info("Rename successful");
                    } else {
                        log.warn("Rename failed");
                    }
                } catch (Exception e) {
                    log.warn(e.getMessage());
                }
            }
        }
    }

    public static synchronized String getFileName() {
        if (counterContentName == 5_000) {
            counterContentName = 0;
        }
        String[] mimeTypeArr = MP4.getType().split("/");
        counterContentName++;
        return "valerrianych_bot-" + mimeTypeArr[0] + "-" + counterContentName + "-" +
                LocalDate.now() + "." + mimeTypeArr[1];
    }
}
