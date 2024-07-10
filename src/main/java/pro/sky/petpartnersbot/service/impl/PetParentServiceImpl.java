package pro.sky.petpartnersbot.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.petpartnersbot.entity.PetParent;
import pro.sky.petpartnersbot.repository.PetParentRepository;
import pro.sky.petpartnersbot.service.PetParentService;


@Service
@RequiredArgsConstructor
public class PetParentServiceImpl implements PetParentService {
    private final Logger logger = LoggerFactory.getLogger(PetParentServiceImpl.class);
    private final PetParentRepository repository;

    @Override
    public PetParent findByChatId(Long chatId) {
        logger.info("Was invoked find PetParent by ChatId method");
        return repository.findByChatId(chatId);
    }

    @Override
    public void addParent(PetParent petParent){
        logger.info("Was invoked create PetParent method");
        repository.save(petParent);
    }

    @Override
    public void deleteParent(long id){
        logger.info("Was invoked delete PetParent method");
        repository.deleteById(id);
    }
}
