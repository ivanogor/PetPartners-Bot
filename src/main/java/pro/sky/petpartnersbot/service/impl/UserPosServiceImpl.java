package pro.sky.petpartnersbot.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.petpartnersbot.entity.UserPos;
import pro.sky.petpartnersbot.repository.UserPosRepository;
import pro.sky.petpartnersbot.service.UserPosService;

@Service
@RequiredArgsConstructor
public class UserPosServiceImpl implements UserPosService {
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserPosRepository repository;

    public UserPos findByChatId(Long chatId){
        return repository.findByChatId(chatId);
    }

    public void saveUserPos(UserPos userPos){
        repository.save(userPos);
    }
}
