package pro.sky.petpartnersbot.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.petpartnersbot.entity.PropertyDict;
import pro.sky.petpartnersbot.repository.PropertyDictRepository;
import pro.sky.petpartnersbot.service.PropertyDictService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyDictServiceImpl implements PropertyDictService {
    private final Logger logger = LoggerFactory.getLogger(PropertyDictServiceImpl.class);
    private final PropertyDictRepository repository;

    public List<PropertyDict> findByEntity(Long id){
        return repository.getByEntityId(id);
    }

    public PropertyDict findByNameAndEntity(String name,Long id){
        return repository.getByNameAndEntityId(name,id);
    }

    public List<PropertyDict> findFilledByChatIdAndEntityId(Long entity_id,Long chat_id){
        return repository.getChatIdFilled(entity_id,chat_id);
    }
}
