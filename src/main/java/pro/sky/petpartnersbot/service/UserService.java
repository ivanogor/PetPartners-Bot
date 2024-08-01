package pro.sky.petpartnersbot.service;

import pro.sky.petpartnersbot.entity.User;

import java.util.List;

public interface UserService {
    User findById(Long chatId);

    void addUser(User user);

    void deleteUser(long id);

    int checkIfAnyExistByEnt(long id);
    List<User> getAllByEntId(long entity_id);
    User findByUserName(String userName);
}
