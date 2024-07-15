package pro.sky.petpartnersbot.service;

import pro.sky.petpartnersbot.entity.User;

public interface UserService {
    User findById(Long chatId);

    void addUser(User user);

    void deleteUser(long id);
}
