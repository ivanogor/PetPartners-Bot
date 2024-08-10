package pro.sky.petpartnersbot.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.petpartnersbot.entity.Pet;
import pro.sky.petpartnersbot.repository.PetRepository;
import pro.sky.petpartnersbot.service.PetService;

import java.util.List;

/**
 * Сервис для работы с сущностями Pet.
 */
@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {
    private final Logger logger = LoggerFactory.getLogger(PetServiceImpl.class);
    private final PetRepository repository;

    public List<Pet> getAllShltPets(Long chatId){
        logger.info("Was invoked getAllShltPets method");
        return repository.getAllByChatId(chatId);
    }

    public List<Pet> getAllShltPetsWithUser(Long chatId){
        return repository.getAllByChatIdWithUser(chatId);
    }

    public Pet addPet(Pet pet){
        repository.save(pet);
        return pet;
    }
    public Pet findPetByPetId(Long petId){
        logger.info("Was invoked findPetByPetId method");
        return repository.findByPetId(petId);
    }
}
