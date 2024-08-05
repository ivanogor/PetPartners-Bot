package pro.sky.petpartnersbot.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.response.GetFileResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.petpartnersbot.entity.ShelterRoadMap;
import pro.sky.petpartnersbot.entity.User;
import pro.sky.petpartnersbot.repository.ShelterRoadMapRepository;
import pro.sky.petpartnersbot.service.ShelterRoadMapService;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.sql.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ShelterRoadMapServiceImpl implements ShelterRoadMapService {
    private final Logger logger = LoggerFactory.getLogger(ShelterRoadMapServiceImpl.class);
    private final ShelterRoadMapRepository repository;
    private final TelegramBot bot;

    @Override
    public Long switchAct(TelegramBot bot, Update update, User user) {
        if (update.message().document() != null) {
            return uploadDocument(bot, update);
        } else if (update.message().photo() != null) {
            return uploadPhoto(bot, update);
        }
        return null;
    }


    @Override
    public Long uploadPhoto(TelegramBot bot, Update update) {
        logger.info("Was invoked upload shelter road map from photo method");
        String chatId = update.message().chat().id().toString();
        bot.execute(new SendChatAction(chatId, ChatAction.upload_photo));
        String fileId = update.message().photo()[update.message().photo().length - 1].fileId();
        return processFile(bot, fileId, update.message().messageId().toString());
    }

    @Override
    public Long uploadDocument(TelegramBot bot, Update update) {
        logger.info("Was invoked upload shelter road map from document method");
        String chatId = update.message().chat().id().toString();
        bot.execute(new SendChatAction(chatId, ChatAction.upload_photo));
        String fileId = update.message().document().fileId();
        return processFile(bot, fileId, update.message().messageId().toString());
    }

    private Long processFile(TelegramBot bot, String fileId, String messageId) {
        GetFile getFileRequest = new GetFile(fileId);
        GetFileResponse getFileResponse = bot.execute(getFileRequest);
        com.pengrad.telegrambot.model.File file = getFileResponse.file();
        String filePath = file.filePath();
        Long currentId = 0L;

        try {
            File localFile = downloadFile(bot, file);
            currentId = saveFileToDatabase(localFile);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return currentId;
    }

    private File downloadFile(TelegramBot bot, com.pengrad.telegrambot.model.File fileArg)
            throws IOException {
        URL url = new URL(bot.getFullFilePath(fileArg));
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        File file = new File("downloaded_photo.jpg");
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        return file;
    }

    private Long saveFileToDatabase(File fileIo) throws SQLException, IOException {
        Long currentId = 1L;
        while (repository.existsById(currentId)) {
            currentId++;
        }
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/PetParentsDB",
                "newUser", "newUser")) {
            String sql = "INSERT INTO shelter_road_map (road_map_id, image_data) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setLong(1, currentId);
                pstmt.setBinaryStream(2, new FileInputStream(fileIo), (int) fileIo.length());
                pstmt.executeUpdate();
            }
        }
        return currentId;
    }

    @Override
    public ShelterRoadMap findRoadMap(Long shelterId) {
        logger.info("Was invoked find shelter road map method");
        return null;
    }

    @Override
    public void getRoadMap(User user) {
        ShelterRoadMap roadMap = repository.getRoadMap(user.getShlId());
        //System.out.println(roadMap.getMapId());
    }
}
