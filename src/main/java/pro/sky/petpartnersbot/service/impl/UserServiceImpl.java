package pro.sky.petpartnersbot.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.petpartnersbot.entity.User;
import pro.sky.petpartnersbot.repository.UsersRepository;
import pro.sky.petpartnersbot.service.UserService;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UsersRepository repository;


    @Override
    public User findById(Long chatId) {
        logger.info("Was invoked find PetParent by ChatId method");
        return repository.findByChatId(chatId);
    }

    @Override
    public User addUser(User user) {
        logger.info("Was invoked create PetParent method");
        return repository.save(user);
    }

    @Override
    public void deleteUser(long id) {
        logger.info("Was invoked delete PetParent method");
        User obtainedUser = repository.findById(id).orElse(null);
        repository.deleteById(id);
    }

    public int checkIfAnyExistByEnt(long id){
        return repository.getCntByEntity(id);
    }

    public List<User> getAllByEntId(long entity_id){
        return repository.findByEntityId(entity_id);
    }

    public User findByUserName(String userName) {
        logger.info("Was invoked find PetParent by user_name method");
        return repository.findByUserName(userName);
    }
}
