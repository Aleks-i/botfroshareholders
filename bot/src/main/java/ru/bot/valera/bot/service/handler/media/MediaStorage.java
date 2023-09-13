package ru.bot.valera.bot.service.handler.media;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MediaStorage {

    final String SOURCE_PATH_TITS_GIF = "media/gif-tits/";
    final String SOURCE_PATH_TITS_VIDEO = "media/video-tits/";
    final String SOURCE_PATH_GIRLS_VIDEO = "media/video-girls/";

    final String SOURCE_PATH_GOOD_MORNING = "media/good-morning";
    final String SOURCE_PATH_GOOD_NIGHT = "media/good-night";

    final String SOURCE_PATH_MANS_GIF = "media/gif-mans";
    final String SOURCE_PATH_MANS_VIDEO = "media/video-mans";

    final String SOURCE_PATH_FRIDAY = "media/friday";
    final String SOURCE_PATH_ROCK = "media/rock";

    private final Map<String, List<String>> MEDIA_STORAGE_PATHS;

    public MediaStorage() {
        MEDIA_STORAGE_PATHS = new ConcurrentHashMap<>(Map.of(
                SOURCE_PATH_TITS_GIF, new ArrayList<>(),
                SOURCE_PATH_TITS_VIDEO, new ArrayList<>(),
                SOURCE_PATH_GIRLS_VIDEO, new ArrayList<>(),

                SOURCE_PATH_GOOD_MORNING, new ArrayList<>(),
                SOURCE_PATH_GOOD_NIGHT, new ArrayList<>(),

                SOURCE_PATH_MANS_GIF, new ArrayList<>(),
                SOURCE_PATH_MANS_VIDEO, new ArrayList<>(),

                SOURCE_PATH_FRIDAY, new ArrayList<>(),
                SOURCE_PATH_ROCK, new ArrayList<>()
        ));
    }

    public File getFile(String path) {
        List<String> filePaths = MEDIA_STORAGE_PATHS.get(path);
        return new File(Paths.get(filePaths.get(getRandomListIdx(filePaths.size()))).toUri());
    }

    public int getRandomListIdx(int listSize) {
        return ThreadLocalRandom.current().nextInt(0, listSize);
    }
}
