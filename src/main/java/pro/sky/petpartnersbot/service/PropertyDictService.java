package pro.sky.petpartnersbot.service;

import pro.sky.petpartnersbot.entity.PropertyDict;

import java.util.List;

/**
 * Интерфейс для работы со свойствами словаря.
 */
public interface PropertyDictService {

    /**
     * Находит список свойств словаря по идентификатору сущности.
     *
     * @param id Идентификатор сущности.
     * @return Список объектов PropertyDict, соответствующих заданному идентификатору сущности.
     */
    List<PropertyDict> findByEntity(Long id);

    /**
     * Находит свойство словаря по имени и идентификатору сущности.
     *
     * @param name Имя свойства.
     * @param id   Идентификатор сущности.
     * @return Объект PropertyDict, соответствующий заданным имени и идентификатору сущности.
     */
    PropertyDict findByNameAndEntity(String name,Long id);

    /**
     * Находит список заполненных свойств словаря по идентификатору сущности и идентификатору чата.
     *
     * @param entityId Идентификатор сущности.
     * @param chatId Идентификатор чата.
     * @return Список объектов PropertyDict, соответствующих заданным идентификаторам сущности и чата.
     */
    List<PropertyDict> findFilledByChatIdAndEntityId(Long entityId,Long chatId);

    /**
     * Находит список заполненных свойств словаря по идентификатору сущности и идентификатору питомца.
     *
     * @param entityId Идентификатор сущности.
     * @param petId    Идентификатор питомца.
     * @return Список объектов PropertyDict, соответствующих заданным идентификаторам сущности и питомца.
     */
    List<PropertyDict> findFilledByEntityIdAndPetId(Long entityId, Long petId);
}
