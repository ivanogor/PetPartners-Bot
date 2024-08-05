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

    public List<Pet> getAllShltPets(Long chat_id){
        return repository.getAllByChatId(chat_id);
    }

    public void addPet(Pet pet){
        repository.save(pet);
    }

    public Pet findPetBypetIdOrName(Long petId, Long chatId){
        return repository.findByPetIdAndChatId(petId,chatId);
    }
}
