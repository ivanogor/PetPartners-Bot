package pro.sky.petpartnersbot.service;

import pro.sky.petpartnersbot.entity.PetTypeDict;

import java.util.List;

public interface PetTypeDictService {
    List<PetTypeDict> getAllActiveTypes();
    PetTypeDict getTypeById(Long petTypeId);
    PetTypeDict getTypeByName(String name);
}
