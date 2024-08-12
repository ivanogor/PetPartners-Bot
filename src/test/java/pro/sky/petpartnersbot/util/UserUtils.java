package pro.sky.petpartnersbot.util;

import pro.sky.petpartnersbot.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public class UserUtils {
    public static User getUser() {
        return User.builder()
                .userName("util user name")
                .chatId(111L)
                .adoptionDate(LocalDateTime.now())
                .build();
    }

    public static List<User> getUsers() {
        return List.of(getUser(), new User());
    }
}
