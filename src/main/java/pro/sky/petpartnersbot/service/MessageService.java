package pro.sky.petpartnersbot.service;

import pro.sky.petpartnersbot.entity.Message;

public interface MessageService {
    Message findByType(String type);
}
