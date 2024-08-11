package pro.sky.petpartnersbot.service;

import pro.sky.petpartnersbot.entity.UserPet;

/**
 * Интерфейс для работы с сущностями UserPet.
 * Предоставляет методы для сохранения данных о питомцах пользователей.
 */
public interface UserPetService {

    /**
     * Сохраняет информацию о питомце пользователя.
     *
     * @param userPet сущность UserPet, содержащая информацию о питомце пользователя
     */
    void saveUserPet(UserPet userPet);
    UserPet getUserPetByPetId(Long petId);
    UserPet getUserPet(Long chatId);
    void deleteUserPet(UserPet userPet);
}