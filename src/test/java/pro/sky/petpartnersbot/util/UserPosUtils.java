package pro.sky.petpartnersbot.util;

import pro.sky.petpartnersbot.entity.UserPos;

public class UserPosUtils {
    public static UserPos getUserPos() {
        return UserPos.builder()
                .pos("pos")
                .prevPos("prevPos")
                .chatId(123L)
                .build();
    }
}
