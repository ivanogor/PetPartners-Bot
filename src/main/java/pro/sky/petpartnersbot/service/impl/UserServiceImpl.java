package pro.sky.petpartnersbot.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.petpartnersbot.entity.User;
import pro.sky.petpartnersbot.repository.UsersRepository;
import pro.sky.petpartnersbot.service.UserService;

import java.util.List;

/**
 * Сервис для работы с пользователями.
 * Этот класс отвечает за поиск, создание, удаление и другие операции с пользователями.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UsersRepository repository;

    @Override
    public User findById(Long chatId) {
        logger.info("Was invoked findById method");
        return repository.findByChatId(chatId);
    }

    @Override
    public User addUser(User user) {
        logger.info("Was invoked addUser method");
        return repository.save(user);
    }

    @Override
    public void deleteUser(long id) {
        logger.info("Was invoked deleteUser method");
        repository.deleteById(id);
    }

    @Override
    public int checkIfAnyExistByEnt(long id) {
        logger.info("Was invoked checkIfAnyExistByEnt method");
        return repository.getCntByEntity(id);
    }

    @Override
    public List<User> getAllByEntId(long entity_id) {
        logger.info("Was invoked getAllByEntId method");
        return repository.findByEntityId(entity_id);
    }

    @Override
    public User findByUserName(String userName) {
        logger.info("Was invoked findByUserName method");
        return repository.findByUserName(userName);
    }
}