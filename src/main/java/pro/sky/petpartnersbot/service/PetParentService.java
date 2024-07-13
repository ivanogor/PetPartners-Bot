package pro.sky.petpartnersbot.service;

import pro.sky.petpartnersbot.entity.User;

public interface PetParentService {
    User findByChatId(Long chatId);

    void addParent(User user);

    void deleteParent(long id);
}
