package pro.sky.petpartnersbot.service;

import pro.sky.petpartnersbot.entity.User;

import java.util.List;

/**
 * Интерфейс сервиса для работы с пользователями.
 */

public interface UserService {
    User findById(Long chatId);
    User addUser(User user);
    void deleteUser(long id);
    int checkIfAnyExistByEnt(long id);
    List<User> getAllByEntId(long entity_id);
    User findByUserName(String userName);
}
