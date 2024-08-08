package pro.sky.petpartnersbot.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.petpartnersbot.entity.PetTypeDict;
import pro.sky.petpartnersbot.repository.PetTypeDictRepository;
import pro.sky.petpartnersbot.service.PetTypeDictService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetTypeDictSeviceImpl implements PetTypeDictService {
    private final Logger logger = LoggerFactory.getLogger(PetTypeDictSeviceImpl.class);
    private final PetTypeDictRepository repository;

    public List<PetTypeDict> getAllActiveTypes(){
        return repository.getAllActivePetTypes();
    }

    public PetTypeDict getTypeById(Long petTypeId){
        return repository.findByPetTypeId(petTypeId);
    }
    public PetTypeDict getTypeByName(String name){
        return repository.getTypeByName(name);
    }
}
