package pro.sky.petpartnersbot.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.petpartnersbot.entity.PetTypeDict;
import pro.sky.petpartnersbot.repository.PetTypeDictRepository;
import pro.sky.petpartnersbot.service.PetTypeDictService;

import java.util.List;

/**
 * Сервис для работы с типами питомцев.
 */
@Service
@RequiredArgsConstructor
public class PetTypeDictServiceImpl implements PetTypeDictService {
    private final Logger logger = LoggerFactory.getLogger(PetTypeDictServiceImpl.class);
    private final PetTypeDictRepository repository;

    @Override
    public List<PetTypeDict> getAllActiveTypes(){
        logger.info("Was invoked getAllActiveTypes method");
        return repository.getAllActivePetTypes();
    }

    @Override
    public PetTypeDict getTypeById(Long petTypeId){
        logger.info("Was invoked getTypeById method");
        return repository.findByPetTypeId(petTypeId);
    }

    @Override
    public PetTypeDict getTypeByName(String name){
        logger.info("Was invoked getTypeByName method");
        return repository.getTypeByName(name);
    }
}
