package pro.sky.petpartnersbot.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.petpartnersbot.entity.UserPet;
import pro.sky.petpartnersbot.repository.UserPetRepository;
import pro.sky.petpartnersbot.service.UserPetService;

@Service
@RequiredArgsConstructor
public class UserPetServiceImpl implements UserPetService {
    private final Logger logger = LoggerFactory.getLogger(UserPetService.class);
    private final UserPetRepository repository;

    public void saveUserPet(UserPet userPet){
        repository.save(userPet);
    }

    public UserPet getUserPet(Long chatId){
        return repository.findByChatId(chatId);
    }

}
