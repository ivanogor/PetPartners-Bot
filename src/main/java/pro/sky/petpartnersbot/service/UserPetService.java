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

    /**
     * Возвращает информацию о питомце пользователя по идентификатору питомца.
     *
     * @param petId идентификатор питомца
     * @return сущность UserPet, содержащая информацию о питомце пользователя
     */
    UserPet getUserPetByPetId(Long petId);

    /**
     * Возвращает информацию о питомце пользователя по идентификатору чата.
     *
     * @param chatId идентификатор чата пользователя
     * @return сущность UserPet, содержащая информацию о питомце пользователя
     */
    UserPet getUserPet(Long chatId);

    /**
     * Удаляет информацию о питомце пользователя.
     *
     * @param userPet сущность UserPet, содержащая информацию о питомце пользователя
     */
    void deleteUserPet(UserPet userPet);
}