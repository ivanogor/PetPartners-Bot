package pro.sky.petpartnersbot.service;

import pro.sky.petpartnersbot.entity.Pet;

import java.util.List;

/**
 * Интерфейс для работы с сущностями Pet.
 */
public interface PetService {

    /**
     * Получает список всех питомцев по идентификатору чата.
     *
     * @param chatId идентификатор чата
     * @return список питомцев, связанных с данным чатом
     */
    List<Pet> getAllShltPets(Long chatId);

    /**
     * Добавляет нового питомца.
     *
     * @param pet объект Pet, который нужно добавить
     * @return
     */
    Pet addPet(Pet pet);

    /**
     * Находит питомца по его идентификатору.
     *
     * @param petId идентификатор питомца
     * @return объект Pet, соответствующий заданному идентификатору
     */
    Pet findPetByPetId(Long petId);
}
