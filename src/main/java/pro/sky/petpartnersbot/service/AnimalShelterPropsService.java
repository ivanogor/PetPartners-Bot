package pro.sky.petpartnersbot.service;

import pro.sky.petpartnersbot.entity.AnimalShelterProps;

import java.util.List;

/**
 * Интерфейс сервиса для работы со свойствами приютов для животных.
 */
public interface AnimalShelterPropsService {
    /**
     * Получает идентификатор чата волонтеров для указанного приюта.
     *
     * @param animalShelterId идентификатор приюта
     * @return идентификатор чата волонтеров
     */
    
    AnimalShelterProps getUserProp(Long propId, Long animalShelterId);
    List<AnimalShelterProps> getShltListProps(Long animalShelterId);
}
