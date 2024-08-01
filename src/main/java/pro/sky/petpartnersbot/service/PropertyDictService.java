package pro.sky.petpartnersbot.service;

import pro.sky.petpartnersbot.entity.PropertyDict;

import java.util.List;

public interface PropertyDictService {
    List<PropertyDict> findByEntity(Long id);
    PropertyDict findByNameAndEntity(String name,Long id);
    List<PropertyDict> findFilledByChatIdAndEntityId(Long entity_id,Long chat_id);
}
