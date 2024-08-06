package pro.sky.petpartnersbot.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.petpartnersbot.entity.AnimalShelterProps;
import pro.sky.petpartnersbot.repository.AnimalShelterPropsRepository;

/**
 * Сервис для работы со свойствами приютов для животных.
 */
@Service
@RequiredArgsConstructor
public class AnimalShelterPropsServiceImpl {
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final AnimalShelterPropsRepository animalShelterPropsRepository;

    /**
     * Получает свойства приюта для животных по идентификатору свойства и идентификатору приюта.
     *
     * @param propId идентификатор свойства
     * @param animalShelterId идентификатор приюта
     * @return объект AnimalShelterProps, соответствующий заданным параметрам
     */
    public AnimalShelterProps getUserProp(Long propId, Long animalShelterId) {
        logger.info("Was invoked getVolunteerChat method");
        return animalShelterPropsRepository.getUserProp(propId, animalShelterId);
    }

    public AnimalShelterProps getPetProp(Long propId, Long petId) {
        logger.info("Was invoked getVolunteerChat method");
        return animalShelterPropsRepository.getPetProp(propId, petId);
    }

    /**
     * Добавляет новое свойство приюта для животных.
     *
     * @param animalShelterProps объект AnimalShelterProps, который нужно добавить
     */
    public void addShelterProp(AnimalShelterProps animalShelterProps) {
        animalShelterPropsRepository.save(animalShelterProps);
    }
}