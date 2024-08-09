package pro.sky.petpartnersbot.util;

import pro.sky.petpartnersbot.entity.Photo;

public class PhotoUtils {
    public static Photo getPhoto() {
        return Photo.builder()
                .photoId(1L)
                .fileSize(2L)
                .filePath("filePath")
                .fileSize(3L)
                .data(new byte[]{1, 2, 3})
                .mediaType("mediatype")
                .petId(4L)
                .chatId(5L)
                .build();
    }
}
