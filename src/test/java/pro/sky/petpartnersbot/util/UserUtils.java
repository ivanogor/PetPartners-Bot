package pro.sky.petpartnersbot.util;

import pro.sky.petpartnersbot.entity.User;

import java.time.LocalDateTime;

public class UserUtils {
    public static User getUser(){
        return User.builder()
                .userName("")
                .chatId(13123123L)
                .adoptionDate(LocalDateTime.now())
                .build();
    }

}
