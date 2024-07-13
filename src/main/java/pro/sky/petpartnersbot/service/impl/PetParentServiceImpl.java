package pro.sky.petpartnersbot.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.petpartnersbot.entity.User;
import pro.sky.petpartnersbot.repository.UserRepository;
import pro.sky.petpartnersbot.service.PetParentService;


/**
 * Сервис для управления объектами User.
 * Этот сервис предоставляет методы для поиска, создания и удаления записей о родителях питомцев.
 */
@Service
@RequiredArgsConstructor
public class PetParentServiceImpl implements PetParentService {
    private final Logger logger = LoggerFactory.getLogger(PetParentServiceImpl.class);
    private final UserRepository repository;

    /**
     * Находит родителя питомца по заданному идентификатору чата.
     *
     * @param chatId Идентификатор чата, по которому ищется родитель питомца.
     * @return Объект User, соответствующий заданному идентификатору чата, или null, если такой записи нет.
     */
    @Override
    public User findByChatId(Long chatId) {
        logger.info("Was invoked find User by ChatId method");
        return repository.findByChatId(chatId);
    }

    /**
     * Создает новую запись о родителе питомца.
     *
     * @param user Объект User, который нужно сохранить.
     */
    @Override
    public void addParent(User user){
        logger.info("Was invoked create User method");
        repository.save(user);
    }

    /**
     * Удаляет запись о родителе питомца по заданному идентификатору.
     *
     * @param id Идентификатор записи, которую нужно удалить.
     */
    @Override
    public void deleteParent(long id){
        logger.info("Was invoked delete User method");
        repository.deleteById(id);
    }
}
