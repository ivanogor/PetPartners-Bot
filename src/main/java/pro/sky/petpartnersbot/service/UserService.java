package pro.sky.petpartnersbot.service;

import pro.sky.petpartnersbot.entity.User;

import java.util.List;

/**
 * Интерфейс сервиса для работы с пользователями.
 */

public interface UserService {

    /**
     * Находит пользователя по идентификатору чата.
     *
     * @param chatId Идентификатор чата.
     * @return Объект User, соответствующий заданному идентификатору чата.
     */
    User findById(Long chatId);

    /**
     * Добавляет нового пользователя.
     *
     * @param user Объект User, который нужно добавить.
     * @return Объект User, который был добавлен.
     */
    User addUser(User user);

    /**
     * Удаляет пользователя по идентификатору.
     *
     * @param id Идентификатор пользователя.
     */
    void deleteUser(long id);

    /**
     * Проверяет, существуют ли пользователи по идентификатору сущности.
     *
     * @param id Идентификатор сущности.
     * @return Количество пользователей, соответствующих заданному идентификатору сущности.
     */
    int checkIfAnyExistByEnt(long id);

    /**
     * Получает список пользователей по идентификатору сущности.
     *
     * @param entity_id Идентификатор сущности.
     * @return Список объектов User, соответствующих заданному идентификатору сущности.
     */
    List<User> getAllByEntId(long entity_id);

    /**
     * Находит пользователя по имени пользователя.
     *
     * @param userName Имя пользователя.
     * @return Объект User, соответствующий заданному имени пользователя.
     */
    User findByUserName(String userName);
}
