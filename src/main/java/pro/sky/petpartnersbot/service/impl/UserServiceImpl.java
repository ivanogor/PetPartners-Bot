package pro.sky.petpartnersbot.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.petpartnersbot.entity.User;
import pro.sky.petpartnersbot.exception.UserNotFoundException;
import pro.sky.petpartnersbot.repository.UsersRepository;
import pro.sky.petpartnersbot.service.UserService;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UsersRepository repository;


    @Override
    public User findById(Long chatId) {
        logger.info("Was invoked find PetParent by ChatId method");
        return repository.findById(chatId).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void addUser(User user) {
        logger.info("Was invoked create PetParent method");
        repository.save(user);
    }

    @Override
    public void deleteUser(long id) {
        logger.info("Was invoked delete PetParent method");
        repository.deleteById(id);
    }
}
