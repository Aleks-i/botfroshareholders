package ru.bot.valera.bot.service.handlers.media;

import lombok.Getter;

@Getter
public enum MimeType {

    GIF("image/gif"),
    JPEG("image/jpeg"),
    MP4("video/mp4");

    private final String type;
    MimeType(String type) {
        this.type = type;
    }
}
