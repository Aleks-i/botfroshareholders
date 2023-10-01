package ru.bot.valera.bot.service.handlers.media;

import lombok.Getter;

@Getter
public enum SourcePaths {

    SOURCE_PATH_TITS_GIF("media/gif-tits"),
    SOURCE_PATH_TITS_VIDEO("media/video-tits"),
    SOURCE_PATH_GIRLS_VIDEO("media/video-girls"),

    SOURCE_PATH_GOOD_MORNING("media/good-morning"),
    SOURCE_PATH_GOOD_NIGHT("media/good-night"),

    SOURCE_PATH_MANS_GIF("media/gif-mans"),
    SOURCE_PATH_MANS_VIDEO("media/video-mans"),

    SOURCE_PATH_ROCK("media/rock"),
    SOURCE_PATH_FRIDAY("media/friday");

    private final String sourcePath;

    SourcePaths(String sourcePath) {
        this.sourcePath = sourcePath;
    }
}
