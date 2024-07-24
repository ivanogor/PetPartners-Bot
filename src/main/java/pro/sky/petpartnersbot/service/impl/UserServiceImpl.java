package pro.sky.petpartnersbot.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.petpartnersbot.entity.User;
import pro.sky.petpartnersbot.repository.UsersRepository;
import pro.sky.petpartnersbot.service.UserService;


/*
 * Сервис для управления объектами User.
 * Этот сервис предоставляет методы для поиска, создания и удаления записей о родителях питомцев.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UsersRepository repository;


    /**
     * Находит родителя питомца по заданному идентификатору чата.
     *
     * @param chatId Идентификатор чата, по которому ищется родитель питомца.
     * @return Объект PetParent, соответствующий заданному идентификатору чата, или null, если такой записи нет.
     */
    @Override
    public User findById(Long chatId) {
        logger.info("Was invoked find PetParent by ChatId method");
        return repository.findByChatId(chatId);
    }

    /**
     * Создает новую запись о родителе питомца.
     *
     * @param user Объект PetParent, который нужно сохранить.
     */
    @Override
    public void addUser(User user) {
        logger.info("Was invoked create PetParent method");
        repository.save(user);
    }

    /**
     * Удаляет запись о родителе питомца по заданному идентификатору.
     *
     * @param id Идентификатор записи, которую нужно удалить.
     */
    @Override
    public void deleteUser(long id) {
        logger.info("Was invoked delete PetParent method");
        repository.deleteById(id);
    }
}
