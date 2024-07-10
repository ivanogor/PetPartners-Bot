package pro.sky.petpartnersbot.service;

import pro.sky.petpartnersbot.entity.PetParent;

public interface PetParentService {
    PetParent findByChatId(Long chatId);

    void addParent(PetParent petParent);

    void deleteParent(long id);
}
