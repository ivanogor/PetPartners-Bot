package pro.sky.petpartnersbot.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.petpartnersbot.entity.Pet;
import pro.sky.petpartnersbot.repository.PetRepository;
import pro.sky.petpartnersbot.service.PetServise;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetServise {
    private final Logger logger = LoggerFactory.getLogger(PetServiceImpl.class);
    private final PetRepository repository;

    public List<Pet> getAllShltPets(Long chatId){
        return repository.getAllByChatId(chatId);
    }

    public List<Pet> getAllShltPetsWithUser(Long chatId){
        return repository.getAllByChatIdWithUser(chatId);
    }

    public void addPet(Pet pet){
        repository.save(pet);
    }

    public Pet findPetBypetId(Long petId){
        return repository.findByPetId(petId);
    }
}
