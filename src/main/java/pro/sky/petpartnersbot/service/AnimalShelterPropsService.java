package pro.sky.petpartnersbot.service;

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
    String getVolunteerChat(Long animalShelterId);
}