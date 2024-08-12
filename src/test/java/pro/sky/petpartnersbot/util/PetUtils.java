package pro.sky.petpartnersbot.util;

import pro.sky.petpartnersbot.entity.Pet;

import java.util.List;

public class PetUtils {
    public static Pet getPet() {
        return Pet.builder()
                .name("name")
                .petId(12L)
                .petTypeId(2L)
                .chatId(1L)
                .build();
    }

    public static List<Pet> getPets() {
        return List.of(getPet(), new Pet());
    }
}
