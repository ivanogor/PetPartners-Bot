package pro.sky.petpartnersbot.util;

import pro.sky.petpartnersbot.entity.Pet;

public class PetUtils {
    public static Pet getPet() {
        return Pet.builder()
                .petId(11L)
                .petTypeId(22L)
                .build();
    }
}
