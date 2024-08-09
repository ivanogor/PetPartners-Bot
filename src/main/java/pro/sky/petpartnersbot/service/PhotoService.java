package pro.sky.petpartnersbot.service;

import com.pengrad.telegrambot.model.File;
import pro.sky.petpartnersbot.entity.Photo;

import java.io.IOException;

/**
 * Интерфейс для работы с фотографиями питомцев и чатов.
 */
public interface PhotoService {

    /**
     * Загружает фотографию для питомца или чата.
     *
     * @param petId идентификатор питомца
     * @param chatId идентификатор чата
     * @param photoFile файл фотографии
     * @param token токен для доступа к API Telegram
     * @throws IOException если возникла ошибка ввода-вывода
     */
    void uploadPhoto(Long petId,Long chatId, File photoFile, String token) throws IOException;

    /**
     * Находит фотографию по идентификатору чата.
     *
     * @param chatId идентификатор чата
     * @return объект Photos, соответствующий заданному идентификатору чата
     */
    Photo findPhotoByChatId (long chatId);

    /**
     * Находит фотографию по идентификатору питомца.
     *
     * @param petId идентификатор питомца
     * @return объект Photos, соответствующий заданному идентификатору питомца
     */
    Photo findPhotoByPetId (long petId);
}
