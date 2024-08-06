package pro.sky.petpartnersbot.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.petpartnersbot.entity.PropertyDict;
import pro.sky.petpartnersbot.repository.PropertyDictRepository;
import pro.sky.petpartnersbot.service.PropertyDictService;

import java.util.List;

/**
 * Сервис для работы со свойствами словаря.
 * Этот класс отвечает за поиск свойств словаря по различным критериям.
 */
@Service
@RequiredArgsConstructor
public class PropertyDictServiceImpl implements PropertyDictService {
    private final Logger logger = LoggerFactory.getLogger(PropertyDictServiceImpl.class);
    private final PropertyDictRepository repository;

    /**
     * Находит список свойств словаря по идентификатору сущности.
     *
     * @param id Идентификатор сущности.
     * @return Список объектов PropertyDict, соответствующих заданному идентификатору сущности.
     */
    public List<PropertyDict> findByEntity(Long id) {
        return repository.getByEntityId(id);
    }

    /**
     * Находит свойство словаря по имени и идентификатору сущности.
     *
     * @param name Имя свойства.
     * @param id   Идентификатор сущности.
     * @return Объект PropertyDict, соответствующий заданным имени и идентификатору сущности.
     */
    public PropertyDict findByNameAndEntity(String name, Long id) {
        return repository.getByNameAndEntityId(name, id);
    }

    /**
     * Находит список заполненных свойств словаря по идентификатору сущности и идентификатору чата.
     *
     * @param chatId Идентификатор сущности.
     * @param chatId   Идентификатор чата.
     * @return Список объектов PropertyDict, соответствующих заданным идентификаторам сущности и чата.
     */
    public List<PropertyDict> findFilledByChatIdAndEntityId(Long entityId, Long chatId) {
        return repository.getChatIdFilled(entityId, chatId);
    }
}