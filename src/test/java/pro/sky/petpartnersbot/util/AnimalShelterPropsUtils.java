package pro.sky.petpartnersbot.util;

import pro.sky.petpartnersbot.entity.AnimalShelterProps;

public class AnimalShelterPropsUtils {
    public static AnimalShelterProps getAnimalShelterProps() {
        return AnimalShelterProps.builder()
                .propId(1L)
                .animalSheltersPropsId(2L)
                .propVal("propVal")
                .petId(3L)
                .build();
    }
}
