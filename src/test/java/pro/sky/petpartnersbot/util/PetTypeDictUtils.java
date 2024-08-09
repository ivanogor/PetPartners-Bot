package pro.sky.petpartnersbot.util;

import pro.sky.petpartnersbot.entity.PetTypeDict;

public class PetTypeDictUtils {
    public static PetTypeDict getPetTypeDict(){
        return PetTypeDict.builder()
                .petTypeId(111L)
                .name("name")
                .build();
    }
}
