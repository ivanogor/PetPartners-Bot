package pro.sky.petpartnersbot.service;

import pro.sky.petpartnersbot.entity.Pet;

import java.util.List;

public interface PetServise {
    List<Pet> getAllShltPets(Long chat_id);
    Pet findPetBypetIdOrName(Long petId, Long chatId);
}
