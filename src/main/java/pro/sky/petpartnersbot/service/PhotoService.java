package pro.sky.petpartnersbot.service;

import com.pengrad.telegrambot.model.File;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.petpartnersbot.entity.Photos;

import java.io.IOException;

public interface PhotoService {
    void uploadPhoto(Long petId,Long chatId, File photoFile, String token) throws IOException;
    Photos findPhotoByChatId (long chatId);
    Photos findPhotoByPetId (long petId);
}
