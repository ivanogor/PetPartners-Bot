package pro.sky.petpartnersbot.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.petpartnersbot.entity.UserPet;
import pro.sky.petpartnersbot.repository.UserPetRepository;
import pro.sky.petpartnersbot.service.UserPetService;

/**
 * Сервис для работы с сущностями UserPet.
 */
@Service
@RequiredArgsConstructor
public class UserPetServiceImpl implements UserPetService {
    private final Logger logger = LoggerFactory.getLogger(UserPetService.class);
    private final UserPetRepository repository;

    public void saveUserPet(UserPet userPet){
        logger.info("Was invoked saveUserPet method");
        repository.save(userPet);
    }

    public void deleteUserPet(UserPet userPet){
        logger.info("Was invoked deleteUserPet method");
        repository.delete(userPet);
    }

    public UserPet getUserPet(Long chatId){
        logger.info("Was invoked getUserPet method");
        return repository.findByChatId(chatId);
    }

    public UserPet getUserPetByPetId(Long petId){
        logger.info("Was invoked getUserPetByPetId method");
        return repository.findByPetId(petId);
    }

}
