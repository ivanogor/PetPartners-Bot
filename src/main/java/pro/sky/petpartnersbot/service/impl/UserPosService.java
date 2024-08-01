package pro.sky.petpartnersbot.service.impl;

import pro.sky.petpartnersbot.entity.UserPos;

public interface UserPosService {
    UserPos findByChatId(Long chatId);
}
