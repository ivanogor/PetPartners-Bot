package pro.sky.petpartnersbot.service;

import pro.sky.petpartnersbot.entity.User;

/**
 * Интерфейс сервиса для работы с пользователями.
 */
public interface UserService {
    /**
     * Находит пользователя по его идентификатору.
     *
     * @param chatId идентификатор пользователя
     * @return найденный пользователь
     */
    User findById(Long chatId);

    /**
     * Добавляет нового пользователя.
     *
     * @param user пользователь для добавления
     */
    void addUser(User user);

    /**
     * Удаляет пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя
     */
    void deleteUser(long id);
}