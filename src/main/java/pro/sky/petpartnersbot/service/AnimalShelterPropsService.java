package pro.sky.petpartnersbot.service;

import pro.sky.petpartnersbot.entity.AnimalShelterProps;

/**
 * Интерфейс для работы со свойствами приютов для животных.
 */
public interface AnimalShelterPropsService {
    /**
     * Получает свойства приюта для животных по идентификатору свойства и идентификатору приюта.
     *
     * @param propId идентификатор свойства
     * @param animalShelterId идентификатор приюта
     * @return объект AnimalShelterProps, соответствующий заданным параметрам
     */
    AnimalShelterProps getUserProp(Long propId, Long animalShelterId);

    /**
     * Получает свойства животного по идентификатору свойства и идентификатору животного.
     *
     * @param propId идентификатор свойства
     * @param petId идентификатор животного
     * @return объект AnimalShelterProps, соответствующий заданным параметрам
     */
    AnimalShelterProps getPetProp(Long propId, Long petId);

    /**
     * Добавляет новое свойство приюта для животных.
     *
     * @param animalShelterProps объект AnimalShelterProps, который нужно добавить
     * @return объект AnimalShelterProps, сохраненный в репозитории
     */
    AnimalShelterProps addShelterProp(AnimalShelterProps animalShelterProps);
}
