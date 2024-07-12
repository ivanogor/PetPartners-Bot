package pro.sky.petpartnersbot.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.petpartnersbot.entity.PetParent;
import pro.sky.petpartnersbot.repository.PetParentRepository;
import pro.sky.petpartnersbot.service.PetParentService;


/**
 * Сервис для управления объектами PetParent.
 * Этот сервис предоставляет методы для поиска, создания и удаления записей о родителях питомцев.
 */
@Service
@RequiredArgsConstructor
public class PetParentServiceImpl implements PetParentService {
    private final Logger logger = LoggerFactory.getLogger(PetParentServiceImpl.class);
    private final PetParentRepository repository;

    /**
     * Находит родителя питомца по заданному идентификатору чата.
     *
     * @param chatId Идентификатор чата, по которому ищется родитель питомца.
     * @return Объект PetParent, соответствующий заданному идентификатору чата, или null, если такой записи нет.
     */
    @Override
    public PetParent findByChatId(Long chatId) {
        logger.info("Was invoked find PetParent by ChatId method");
        return repository.findByChatId(chatId);
    }

    /**
     * Создает новую запись о родителе питомца.
     *
     * @param petParent Объект PetParent, который нужно сохранить.
     */
    @Override
    public void addParent(PetParent petParent){
        logger.info("Was invoked create PetParent method");
        repository.save(petParent);
    }

    /**
     * Удаляет запись о родителе питомца по заданному идентификатору.
     *
     * @param id Идентификатор записи, которую нужно удалить.
     */
    @Override
    public void deleteParent(long id){
        logger.info("Was invoked delete PetParent method");
        repository.deleteById(id);
    }
}
