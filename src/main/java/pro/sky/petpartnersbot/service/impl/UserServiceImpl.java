package pro.sky.petpartnersbot.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.petpartnersbot.entity.User;
import pro.sky.petpartnersbot.repository.UsersRepository;
import pro.sky.petpartnersbot.service.UserService;

import java.util.List;
import java.util.Objects;

/**
 * Сервис для работы с пользователями.
 * Этот класс отвечает за поиск, создание, удаление и другие операции с пользователями.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UsersRepository repository;

    /**
     * Находит пользователя по идентификатору чата.
     *
     * @param chatId Идентификатор чата.
     * @return Объект User, соответствующий заданному идентификатору чата.
     */
    @Override
    public User findById(Long chatId) {
        logger.info("Was invoked find PetParent by ChatId method");
        return repository.findByChatId(chatId);
    }

    /**
     * Добавляет нового пользователя.
     *
     * @param user Объект User, который нужно добавить.
     * @return Объект User, который был добавлен.
     */
    @Override
    public User addUser(User user) {
        logger.info("Was invoked create PetParent method");
        return repository.save(user);
    }

    /**
     * Удаляет пользователя по идентификатору.
     *
     * @param id Идентификатор пользователя.
     */
    @Override
    public void deleteUser(long id) {
        logger.info("Was invoked delete PetParent method");
        User obtainedUser = repository.findById(id).orElse(null);
        repository.deleteById(id);
    }

    /**
     * Проверяет, существуют ли пользователи по идентификатору сущности.
     *
     * @param id Идентификатор сущности.
     * @return Количество пользователей, соответствующих заданному идентификатору сущности.
     */
    public int checkIfAnyExistByEnt(long id) {
        return repository.getCntByEntity(id);
    }

    /**
     * Получает список пользователей по идентификатору сущности.
     *
     * @param entity_id Идентификатор сущности.
     * @return Список объектов User, соответствующих заданному идентификатору сущности.
     */
    public List<User> getAllByEntId(long entity_id) {
        return repository.findByEntityId(entity_id);
    }

    /**
     * Находит пользователя по имени пользователя.
     *
     * @param userName Имя пользователя.
     * @return Объект User, соответствующий заданному имени пользователя.
     */
    public User findByUserName(String userName) {
        logger.info("Was invoked find PetParent by user_name method");
        return repository.findByUserName(userName);
    }
}