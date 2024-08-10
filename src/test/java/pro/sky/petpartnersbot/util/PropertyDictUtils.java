package pro.sky.petpartnersbot.util;

import pro.sky.petpartnersbot.entity.PropertyDict;

import java.time.LocalDateTime;
import java.util.List;

public class PropertyDictUtils {
    public static PropertyDict getPropertyDict() {
        return PropertyDict.builder()
                .name("name")
                .dateFrom(LocalDateTime.now())
                .dateTo(LocalDateTime.now())
                .entityId(123L)
                .build();
    }

    public static List<PropertyDict> getPropertyDictList() {
        return List.of(getPropertyDict(), new PropertyDict());
    }
}
