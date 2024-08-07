package pro.sky.petpartnersbot.service.impl;

import com.pengrad.telegrambot.model.File;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.petpartnersbot.entity.Photos;
import pro.sky.petpartnersbot.repository.PhotoRepository;
import pro.sky.petpartnersbot.service.PhotoService;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static pro.sky.petpartnersbot.service.utils.MimeTypeByExtention.getMimeType;

/**
 * Сервис для работы с фотографиями питомцев и чатов.
 */
@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {
    private final Logger logger = LoggerFactory.getLogger(PhotoServiceImpl.class);
    private final PhotoRepository repository;

    @Override
    @Transactional
    public Photos findPhotoByPetId (long petId) {
        logger.info("Was invoked findPhotoByPetId method");
        return repository.findByPetId(petId);
    }

    @Override
    @Transactional
    public Photos findPhotoByChatId (long chatId) {
        logger.info("Was invoked findPhotoByChatId method");
        return repository.findByChatId(chatId);
    }

    @Override
    @Transactional
    public void uploadPhoto(Long petId,Long chatId, File photoFile, String token) throws IOException {
        Photos photo;
        logger.info("Was invoked uploadPhoto method");
        DiskFileItem fileItem;
        String fileUrl = String.format("https://api.telegram.org/file/bot%s/%s", token, photoFile.filePath());
        try (InputStream in = new URL(fileUrl).openStream()) {
            String prefix = photoFile.filePath().substring(photoFile.filePath().lastIndexOf("/")+1,photoFile.filePath().lastIndexOf(".")) ;
            String suffix = photoFile.filePath().substring(photoFile.filePath().lastIndexOf(".")+1) ;
            Path tempFile = Files.createTempFile(prefix, suffix);
            Files.copy(in, tempFile, StandardCopyOption.REPLACE_EXISTING);
            String mimeType = getMimeType(suffix);
            if (mimeType == null){
                mimeType = "application/octet-stream";
            }
            fileItem = new DiskFileItem(tempFile.toFile().getName(), mimeType, true, tempFile.toFile().getName(), (int)tempFile.toFile().length(), tempFile.getParent().toFile());
            fileItem.getOutputStream().write(Files.readAllBytes(tempFile));

            if (petId!=null){
                photo = findPhotoByPetId(petId);
                if (photo==null) {
                    photo = Photos.builder()
                            .data(fileItem.get())
                            .fileSize(fileItem.getSize())
                            .mediaType(fileItem.getContentType())
                            .filePath(photoFile.filePath())
                            .petId(petId)
                            .build();
                }else {
                    photo.setData(fileItem.get());
                    photo.setFileSize(fileItem.getSize());
                    photo.setFilePath(photoFile.filePath());
                    photo.setMediaType(fileItem.getContentType());
                }
            }else{
                photo = findPhotoByChatId(chatId);
                if (photo==null) {
                photo = Photos.builder()
                        .data(fileItem.get())
                        .fileSize(fileItem.getSize())
                        .mediaType(fileItem.getContentType())
                        .filePath(photoFile.filePath())
                        .chatId(chatId)
                        .build();
                }else {
                    photo.setData(fileItem.get());
                    photo.setFilePath(photoFile.filePath());
                    photo.setFileSize(fileItem.getSize());
                    photo.setMediaType(fileItem.getContentType());
                }
            }
            repository.save(photo);
        } catch (Exception e) {
            logger.error("Exception occurred: {}, Request Details: petId = {} chatId = {} err: {}",
                    "uploadPhoto",petId,chatId,e.getMessage());
        }
    }
}
