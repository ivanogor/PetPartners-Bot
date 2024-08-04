package pro.sky.petpartnersbot.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.petpartnersbot.entity.ShelterRoadMap;
import pro.sky.petpartnersbot.repository.ShelterRoadMapRepository;
import pro.sky.petpartnersbot.service.ShelterRoadMapService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor
@Transactional
public class ShelterRoadMapServiceImpl implements ShelterRoadMapService {
    private final Logger logger = LoggerFactory.getLogger(ShelterRoadMapServiceImpl.class);
    private final ShelterRoadMapRepository repository;
    private final TelegramBot bot;

    @Override
    public void uploadPhoto(TelegramBot bot, Update update) {
        logger.info("Was invoked upload shelter road map from photo method");
        String chatId = update.message().chat().id().toString();
        bot.execute(new SendChatAction(chatId, ChatAction.upload_photo));
        String fileId = update.message().photo()[update.message().photo().length - 1].fileId();
        processFile(bot, fileId, update.message().messageId().toString());
    }

    @Override
    public void uploadDocument(TelegramBot bot, Update update) {
        logger.info("Was invoked upload shelter road map from document method");

        String chatId = update.message().chat().id().toString();
        bot.execute(new SendChatAction(chatId, ChatAction.upload_photo));
        String fileId = update.message().document().fileId();
        processFile(bot, fileId, update.message().messageId().toString());
    }

    private static void processFile(TelegramBot bot, String fileId, String messageId) {
        GetFile getFileRequest = new GetFile(fileId);
        GetFileResponse getFileResponse = bot.execute(getFileRequest);
        com.pengrad.telegrambot.model.File file = getFileResponse.file();
        String filePath = file.filePath();

        try {
            File localFile = downloadFile(bot, file);
            saveFileToDatabase(bot, localFile, file, messageId);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static File downloadFile(TelegramBot bot, com.pengrad.telegrambot.model.File fileArg)
            throws IOException {
        URL url = new URL(bot.getFullFilePath(fileArg));
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        File file = new File("downloaded_photo.jpg");
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        return file;
    }

    private static void saveFileToDatabase(TelegramBot bot, File fileIo,
                                           com.pengrad.telegrambot.model.File file,
                                           String messageId) throws SQLException, IOException {
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/PetParentsDB",
                "newUser", "newUser")) {
            String sql = "INSERT INTO shelter_road_map (image_data) VALUES (?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setBinaryStream(1, new FileInputStream(fileIo), (int) fileIo.length());
                pstmt.executeUpdate();
            }
        }
    }

    private static void sendNoPhotoMessage(TelegramBot bot, String chatId) {
        SendMessage request = new SendMessage(chatId, "Пожалуйста, отправьте фото или файл с изображением.");
        bot.execute(request);
    }

    @Override
    public ShelterRoadMap findRoadMap(Long shelterId) {
        logger.info("Was invoked find shelter road map method");
        return null;
    }
}
