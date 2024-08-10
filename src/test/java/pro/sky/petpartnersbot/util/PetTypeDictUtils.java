package pro.sky.petpartnersbot.util;

import pro.sky.petpartnersbot.entity.PetTypeDict;

import java.time.LocalDateTime;
import java.util.List;

public class PetTypeDictUtils {
    public static PetTypeDict getPetTypeDict() {
        return PetTypeDict.builder()
                .petTypeId(1L)
                .name("name")
                .dateFrom(LocalDateTime.now())
                .dateTo(LocalDateTime.now())
                .build();
    }

    public static List<PetTypeDict> getPetTypeDictList() {
        return List.of(getPetTypeDict(), new PetTypeDict());
    }
}
