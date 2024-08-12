package pro.sky.petpartnersbot.service;

import pro.sky.petpartnersbot.entity.PetTypeDict;

import java.util.List;

public interface PetTypeDictService {

    /**
     * Получает список всех активных типов питомцев.
     *
     * @return список активных типов питомцев
     */
    List<PetTypeDict> getAllActiveTypes();

    /**
     * Получает тип питомца по его идентификатору.
     *
     * @param petTypeId идентификатор типа питомца
     * @return объект PetTypeDict, соответствующий заданному идентификатору
     */
    PetTypeDict getTypeById(Long petTypeId);

    /**
     * Получает тип питомца по его названию.
     *
     * @param name название типа питомца
     * @return объект PetTypeDict, соответствующий заданному названию
     */
    PetTypeDict getTypeByName(String name);
}
