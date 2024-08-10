package pro.sky.petpartnersbot.util;

import pro.sky.petpartnersbot.entity.UserPet;

public class UserPetUtils {
    public static UserPet getUserPet() {
        return UserPet.builder()
                .petId(1L)
                .chatId(2L)
                .build();
    }
}
