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

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {
    private final Logger logger = LoggerFactory.getLogger(PhotoServiceImpl.class);
    private final PhotoRepository repository;

    @Transactional
    public Photos findPhotoByPetId (long petId) {
        logger.info("Was invoked method for get avatar");
        return repository.findByPetId(petId);
    }

    @Transactional
    public Photos findPhotoByChatId (long chatId) {
        logger.info("Was invoked method for get avatar");
        return repository.findByPetId(chatId);
    }
    @Transactional
    public void uploadPhoto(Long petId,Long chatId, File photoFile, String token) throws IOException {
        Photos photo;
        logger.info("Was invoked method for upload photo");
        DiskFileItem fileItem;
        String fileUrl = String.format("https://api.telegram.org/file/bot%s/%s", token, photoFile.filePath());
        try (InputStream in = new URL(fileUrl).openStream()) {
            Path tempFile = Files.createTempFile(null, null);
            Files.copy(in, tempFile, StandardCopyOption.REPLACE_EXISTING);

            fileItem = new DiskFileItem(tempFile.toFile().getName(), "application/octet-stream", true, tempFile.toFile().getName(), (int)tempFile.toFile().length(), tempFile.getParent().toFile());
            fileItem.getOutputStream().write(Files.readAllBytes(tempFile));

            if (petId!=null){
                photo = findPhotoByPetId(petId);
                if (photo==null) {
                    photo = Photos.builder()
                            .data(fileItem.get())
                            .fileSize(fileItem.getSize())
                            .mediaType(fileItem.getContentType())
                            .filePath(fileItem.getName())
                            .petId(petId)
                            .build();
                }else {
                    photo.setData(fileItem.get());
                    photo.setFileSize(fileItem.getSize());
                    photo.setFilePath(fileItem.getName());
                    photo.setMediaType(fileItem.getContentType());
                }
            }else{
                photo = findPhotoByChatId(chatId);
                if (photo==null) {
                photo = Photos.builder()
                        .data(fileItem.get())
                        .fileSize(fileItem.getSize())
                        .mediaType(fileItem.getContentType())
                        .filePath(fileItem.getName())
                        .chatId(chatId)
                        .build();
                }else {
                    photo.setData(fileItem.get());
                    photo.setFilePath(fileItem.getName());
                    photo.setFileSize(fileItem.getSize());
                    photo.setMediaType(fileItem.getContentType());
                }
            }
            repository.save(photo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getExtensions(String fileName) {
        logger.info("Was invoked method for get extensions of avatar");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
