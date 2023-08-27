package ru.valera.bot.service.handler.media;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MediaStorage {
    public static final String SOURCE_PATH_GIF = "media/gif/";
    public static final String SOURCE_PATH_TITS_VIDEO = "media/video-tits/";
    public static final String SOURCE_PATH_GIRLS_VIDEO = "media/video-girls/";
    public static final Map<String, List<String>> MEDIA_STORAGE_PATHS = new ConcurrentHashMap<>(Map.of(
            SOURCE_PATH_GIF, new ArrayList<>(),
            SOURCE_PATH_TITS_VIDEO, new ArrayList<>(),
            SOURCE_PATH_GIRLS_VIDEO, new ArrayList<>()
    ));
}
