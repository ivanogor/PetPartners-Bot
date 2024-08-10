package pro.sky.petpartnersbot.util;

import pro.sky.petpartnersbot.entity.Photos;

public class PhotoUtils {
    public static Photos getPhoto() {
        return Photos.builder()
                .photoId(1L)
                .fileSize(2L)
                .filePath("/fileDir/filePath.ext")
                .fileSize(3L)
                .data(new byte[]{1, 2, 3})
                .mediaType("mediatype")
                .petId(4L)
                .chatId(5L)
                .build();
    }
}
