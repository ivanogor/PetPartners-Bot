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

    @Override
    public List<PropertyDict> findByEntity(Long id) {
        logger.info("Was invoked findByEntity method");
        return repository.getByEntityId(id);
    }

    @Override
    public PropertyDict findByNameAndEntity(String name, Long id) {
        logger.info("Was invoked findByNameAndEntity method");
        return repository.getByNameAndEntityId(name, id);
    }

    @Override
    public List<PropertyDict> findFilledByChatIdAndEntityId(Long entityId, Long chatId) {
        logger.info("Was invoked findFilledByChatIdAndEntityId method");
        return repository.getChatIdFilled(entityId, chatId);
    }

    @Override
    public List<PropertyDict> findFilledByEntityIdAndPetId(Long entityId, Long petId) {
        logger.info("Was invoked findFilledByEntityIdAndPetId method");
        return repository.getFilledByEntityIdAndPetId(entityId, petId);
    }
}