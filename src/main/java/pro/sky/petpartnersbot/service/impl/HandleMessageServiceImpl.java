package pro.sky.petpartnersbot.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.*;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.pengrad.telegrambot.response.SendResponse;
import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;

import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.petpartnersbot.configuration.TelegramBotConsts;
import pro.sky.petpartnersbot.entity.*;
import pro.sky.petpartnersbot.service.HandleMessageService;
import pro.sky.petpartnersbot.service.utils.KeyboardsForAnswer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import static com.pengrad.telegrambot.model.request.ParseMode.HTML;
import static pro.sky.petpartnersbot.service.utils.MimeTypeByExtention.getMimeType;

/**
 * Сервис для обработки сообщений от Telegram бота.
 * Этот класс отвечает за анализ и обработку входящих сообщений, а также за взаимодействие с пользователем через Telegram.
 */
@Service
@RequiredArgsConstructor
public class HandleMessageServiceImpl implements HandleMessageService {
    private final Logger logger = LoggerFactory.getLogger(HandleMessageServiceImpl.class);
    private final TelegramBot bot;
    private final UserServiceImpl userService;
    private final MessageServiceImpl messageService;
    private final PropertyDictServiceImpl propertyDictService;
    private final AnimalShelterPropsServiceImpl animalShelterPropsService;
    private final UserPosServiceImpl userPosService;
    private final PetServiceImpl petService;
    private final PetTypeDictSeviceImpl petTypeDictSevice;
    private final PhotoServiceImpl photoService;

    /**
     * Обрабатывает входящее сообщение от Telegram API.
     * Если пользователь впервые использует чат, добавляет его в базу данных и переходит к анализу сообщений.
     * Иначе перенаправляет пользователя к выбору приюта.
     *
     * @param update Входящее обновление от Telegram API.
     */
    @Override
    public void handleMessage(Update update) {
        if (update.message() != null) {
            logger.info("Handling message");
            String updateText = update.message().text();
            Long chatId = update.message().chat().id();
            processText(updateText, chatId, update);
        }
    }

    /**
     * Анализирует текст сообщения и выполняет соответствующие действия.
     *
     * @param updateText Текст сообщения.
     * @param chatId     Идентификатор чата.
     */
    private void processText(String updateText, Long chatId, Update update) {
        logger.info("Was invoked switching message with text method");

        UserPos userPos = userPosService.findByChatId(chatId);
        User foundedUser = userService.findById(chatId);

        if (updateText==null){
            updateText="";
        }
        switchFunc(updateText,foundedUser,update,chatId,userPos);

    }

    /**
     * Получает меню свойств приюта для животных.
     *
     * @param chatId Идентификатор чата.
     * @param pos    Текущая позиция пользователя.
     */
    private void getShltPropsMenu(Long chatId, String pos,String prevPos,Long entityId) {
        SendMessage message;
        SendResponse response;
        List<PropertyDict> shltProps = propertyDictService.findByEntity(entityId);
        if (shltProps.isEmpty()) {
            message = new SendMessage(chatId, "Действующих разделов о приюте " +
                    "не найдено свяжитесь с администратором бота").replyMarkup(new ReplyKeyboardMarkup("Обновить").resizeKeyboard(true)
                    .oneTimeKeyboard(false));
        } else {
            String messageText;
            ReplyKeyboardMarkup propsKeyboard = new ReplyKeyboardMarkup("").resizeKeyboard(true)
                    .oneTimeKeyboard(false);

            for (PropertyDict shltProp : shltProps) {
                propsKeyboard.addRow(new KeyboardButton(shltProp.getName()));
            }
            if (entityId == TelegramBotConsts.shelt){
                messageText = messageService.findById("addShltInfo").getText();

                propsKeyboard.addRow(new KeyboardButton("Схема проезда"))
                        .addRow(new KeyboardButton("Назад"))
                        .addRow(new KeyboardButton("Удалить учетную запись"));
            }else{
                messageText = messageService.findById("showPetsProps").getText();

                propsKeyboard.addRow(new KeyboardButton("Имя питомца"))
                        .addRow(new KeyboardButton("Возраст питомца"))
                        .addRow(new KeyboardButton("Тип питомца"))
                        .addRow(new KeyboardButton("Фото питомца"))
                        .addRow(new KeyboardButton("Удалить питомца"))
                        .addRow(new KeyboardButton("Назад"));
            }
            message = new SendMessage(chatId, messageText).replyMarkup(propsKeyboard);

            saveUserPos(chatId,pos,prevPos);
        }

        response = bot.execute(message);
        checkResponse(response);
    }

    /**
     * Получает меню клиента.
     *
     * @param chatId Идентификатор чата.
     * @param pos    Текущая позиция пользователя.
     */
    private void getClientMenu(Long chatId, String pos) {
        SendMessage message;
        SendResponse response;
        List<User> shltList = userService.getAllByEntId(TelegramBotConsts.shelt);
        if (shltList.isEmpty()) {
            String messageText = EmojiParser.parseToUnicode(messageService.findById("noAnyShltReg").getText());
            message = new SendMessage(chatId, messageText).replyMarkup(KeyboardsForAnswer.NO_INFO_KEYBOARD);
            saveUserPos(chatId, pos, pos);
        } else {
            String messageText = messageService.findById("getShltList").getText();

            ReplyKeyboardMarkup propsKeyboard = new ReplyKeyboardMarkup("").resizeKeyboard(true)
                    .oneTimeKeyboard(false);

            for (User shlt : shltList) {
                propsKeyboard.addRow(new KeyboardButton(shlt.getUserName()));
            }

            propsKeyboard.addRow(new KeyboardButton("Питомцы"))
                    .addRow(new KeyboardButton("Удалить учетную запись"));
            message = new SendMessage(chatId, messageText).replyMarkup(propsKeyboard);
            saveUserPos(chatId, pos, pos);
        }

        response = bot.execute(message);
        checkResponse(response);
    }


    private void getShltPetMenu(Long chatId,String pos){
        SendMessage message;
        SendResponse response;
        List<Pet> shltPetList = petService.getAllShltPets(chatId);

        String messageText = messageService.findById("showShltPets").getText();

        ReplyKeyboardMarkup propsKeyboard = new ReplyKeyboardMarkup("").resizeKeyboard(true)
                .oneTimeKeyboard(false);

        for (Pet shltPet:shltPetList){
            propsKeyboard.addRow(new KeyboardButton(shltPet.getName())) ;
        }

        propsKeyboard.addRow(new KeyboardButton("Добавить питомца"))
                .addRow(new KeyboardButton("Назад"));
        message = new SendMessage(chatId, messageText).replyMarkup(propsKeyboard);
        saveUserPos(chatId,"Все питомцы приюта",pos);

        //Проверка выполнения отправки сообщения
        response = bot.execute(message);
        checkResponse(response);
    }

    /**
     * Получает меню клиента для выбранного приюта.
     *
     * @param chatId Идентификатор чата.
     * @param shltId Идентификатор приюта.
     */
    private void getClientShltMenu(Long chatId, Long shltId) {
        SendMessage message;
        SendResponse response;
        List<PropertyDict> shltProps = propertyDictService.findFilledByChatIdAndEntityId(TelegramBotConsts.shelt, shltId);

        if (shltProps.isEmpty()) {
            String messageText = EmojiParser.parseToUnicode(messageService.findById("noAnyShltInfo").getText());
            message = new SendMessage(chatId, messageText).replyMarkup(KeyboardsForAnswer.NO_INFO_KEYBOARD_CLNT);
            saveUserPos(chatId, "/start", "/start");
        } else {
            String messageText = messageService.findById("showShltInfo").getText();

            ReplyKeyboardMarkup propsKeyboard = new ReplyKeyboardMarkup("").resizeKeyboard(true)
                    .oneTimeKeyboard(false);

            for (PropertyDict shltProp : shltProps) {
                propsKeyboard.addRow(new KeyboardButton(shltProp.getName()));
            }

            propsKeyboard
                    .addRow(new KeyboardButton("Мой номер телефона"))
                    .addRow(new KeyboardButton("Позвать волонтера"))
                    .addRow(new KeyboardButton("Выбрать другой приют"))
                    .addRow(new KeyboardButton("Обновить"))
                    .addRow(new KeyboardButton("Удалить учетную запись"));
            message = new SendMessage(chatId, messageText).replyMarkup(propsKeyboard);
            saveUserPos(chatId, "/start", "/start");
        }

        response = bot.execute(message);
        checkResponse(response);
    }

    /**
     * Сохраняет текущую позицию пользователя.
     *
     * @param chatId  Идентификатор чата.
     * @param pos     Текущая позиция пользователя.
     * @param prevPos Предыдущая позиция пользователя.
     */
    private void saveUserPos(Long chatId, String pos, String prevPos) {
        UserPos userPos = UserPos.builder()
                .chatId(chatId)
                .pos(pos)
                .prevPos(prevPos)
                .build();
        userPosService.saveUserPos(userPos);
    }

    /**
     * Проверяет ответ от Telegram API на корректность.
     *
     * @param response Ответ от Telegram API.
     */
    private void checkResponse(SendResponse response) {
        if (!response.isOk()) {
            logger.error("Response isn't correct. Error code: {}", response.errorCode());
        }
    }

    /**
     * Обрабатывает функциональность в зависимости от параметра.
     *
     * @param param   Параметр для обработки.
     * @param user    Пользователь.
     * @param update  Входящее обновление от Telegram API.
     * @param chatId  Идентификатор чата.
     * @param userPos Текущая позиция пользователя.
     */
    private void switchFunc(String param, User user, Update update, Long chatId, UserPos userPos) {
        SendMessage message;
        SendResponse response;
        String pos = "";
        if (userPos != null) {
            pos = userPos.getPos();
        }
        String prevPos = "";
        if (userPos != null) {
            prevPos = userPos.getPrevPos();
        }
        boolean chekAnyShltExist = userService.checkIfAnyExistByEnt(TelegramBotConsts.shelt) != 0;
        switch (param) {
            case "/start" -> {
                if (Objects.isNull(user)) {
                    String messageText = messageService.findById("welcomeMessage").getText();
                    message = new SendMessage(chatId, messageText).replyMarkup(KeyboardsForAnswer.START_KEYBOARD);
                    response = bot.execute(message);
                    checkResponse(response);
                } else if (user.getEntityId() == TelegramBotConsts.user) {
                    if (user.getShlId() == null) {
                        getClientMenu(chatId, "/start");
                    } else {
                        getClientShltMenu(chatId, user.getShlId());
                    }
                } else if (user.getEntityId()==TelegramBotConsts.shelt) {
                    message = new SendMessage(chatId, "Выберите дальнейшее действие")
                            .replyMarkup(KeyboardsForAnswer.PETS_KEYBOARD);
                    response = bot.execute(message);
                    checkResponse(response);
                    saveUserPos(chatId,"/start","/start");
                }
            }
            case "Приют" -> {
                if (Objects.isNull(user)) {
                    User newUser = User.builder()
                            .chatId(chatId)
                            .userName((update.message().chat().username()))
                            .entityId(TelegramBotConsts.shelt)
                            .build();
                    userService.addUser(newUser);
                }
                message = new SendMessage(chatId, "Выберите дальнейшее действие")
                        .replyMarkup(KeyboardsForAnswer.PETS_KEYBOARD);
                response = bot.execute(message);
                checkResponse(response);
            }
            case "Информация о приюте" -> {
                getShltPropsMenu(chatId,"Информация о приюте","/start",TelegramBotConsts.shelt);
            }
            case "Добавить питомца" -> {
                Pet newPet = Pet.builder()
                        .chatId(chatId)
                        .entityId(TelegramBotConsts.pet)
                        .build();
                petService.addPet(newPet);
                getShltPropsMenu(chatId, String.valueOf(newPet.getPetId()),"Все питомцы приюта",TelegramBotConsts.pet);
            }
            case "Клиент" -> {
                User newUser = User.builder()
                        .chatId(chatId)
                        .entityId(TelegramBotConsts.user)
                        .userName((update.message().chat().username()))
                        .build();
                userService.addUser(newUser);
                getClientMenu(chatId, "/start");
            }
            case "Все питомцы приюта" -> {
                getShltPetMenu(chatId,"/start");
            }
            case "Позвать волонтера" -> {
                AnimalShelterProps prop = animalShelterPropsService.getUserProp(TelegramBotConsts.shltVol, user.getShlId());
                String volChatId = "";
                if (prop != null) {
                    volChatId = prop.getPropVal();
                }

                if (volChatId == "") {
                    message = new SendMessage(chatId, "Приют не указал чат для волонтеров");
                } else {
                    bot.execute(new SendMessage(Long.parseLong(animalShelterPropsService.getUserProp(TelegramBotConsts.shltVol, user.getShlId()).getPropVal())
                            , "Пользователь \n<a href=\"tg://user?id=" + chatId + "\">" +
                            update.message().chat().firstName() + "</a>\nпросит связаться с волонтером").parseMode(HTML));

                    message = new SendMessage(chatId, "Запрос отправлен. С вами свяжутся в течении 10-15 минут");
                }

                response = bot.execute(message);
                checkResponse(response);
                saveUserPos(chatId, "/start", "/start");
            }
            case "Удалить учетную запись" -> {
                userService.deleteUser(chatId);
                message = new SendMessage(chatId, "Учетная запись удалена").replyMarkup(KeyboardsForAnswer.START_KEYBOARD);
                response = bot.execute(message);
                checkResponse(response);
            }
            case "Удалить питомца" ->{
                Long petId = null;
                try {
                    petId = Long.parseLong(pos.substring(pos.indexOf("(") + 1, pos.indexOf(")")));
                }catch(Exception e){
                    logger.error("Exception occurred: {}, Request Details: param = {} err: {}",
                            "switchFunc->PetDel",pos,e.getMessage());
                }
                Pet pet = petService.findPetBypetIdOrName(petId,chatId);

                pet.setDateTo(LocalDateTime.now());
                petService.addPet(pet);
                message = new SendMessage(chatId, "Питомец был удален");
                response = bot.execute(message);
                checkResponse(response);
                saveUserPos(chatId,"Все питомцы приюта","/start");
                switchFunc(prevPos,user,update,chatId,userPos);
            }
            case "Выбрать другой приют" ->{
                user.setShlId(null);
                userService.addUser(user);
                getClientMenu(chatId, "/start");
            }
            case "Мой номер телефона" -> {
                String phone = user.getContacts();
                if (phone == null) {
                    phone = "";
                }
                message = new SendMessage(chatId, "Введите номер телефона в формате +7-9**-***-**-**\n" +
                        "Текущий номер: " + phone).replyMarkup(KeyboardsForAnswer.RETURN_KEYBOARD);
                response = bot.execute(message);
                checkResponse(response);
                saveUserPos(chatId, "Проверить номер", "/start");
            }
            case "Имя питомца" ->{
                Long petId = null;
                try {
                    petId = Long.parseLong(pos.substring(pos.indexOf("(") + 1, pos.indexOf(")")));
                }catch(Exception e){
                    logger.error("Exception occurred: {}, Request Details: param = {} err: {}",
                            "switchFunc->PetName",pos,e.getMessage());
                }
                String petName = petService.findPetBypetIdOrName(petId,chatId).getName();

                if (petName == null){
                    petName="";
                }

                message = new SendMessage(chatId, "Вы можете ввести новое имя питомца\n" +
                        "Текущий имя: "+petName).replyMarkup(KeyboardsForAnswer.RETURN_KEYBOARD);
                response = bot.execute(message);
                checkResponse(response);
                saveUserPos(chatId,"Имя питомца",pos);
            }
            case "Возраст питомца" ->{
                Long petId = null;
                try {
                    petId = Long.parseLong(pos.substring(pos.indexOf("(") + 1, pos.indexOf(")")));
                }catch(Exception e){
                    logger.error("Exception occurred: {}, Request Details: param = {} err: {}",
                            "switchFunc->PetAge",pos,e.getMessage());
                }
                String petAge = petService.findPetBypetIdOrName(petId,chatId).getAge();

                if (petAge==null){
                    petAge = "";
                }

                message = new SendMessage(chatId, "Вы можете ввести возраст питомца\n" +
                        "Текущий возраст: "+petAge).replyMarkup(KeyboardsForAnswer.RETURN_KEYBOARD);
                response = bot.execute(message);
                checkResponse(response);
                saveUserPos(chatId,"Возраст питомца",pos);
            }
            case "Схема проезда" ->{
                Photos photos = photoService.findPhotoByChatId(chatId);

                if (photos==null){
                    message = new SendMessage(chatId, "Вы можете добавить схему проезда\n" +
                            "Текущее фото: ").replyMarkup(KeyboardsForAnswer.RETURN_KEYBOARD);
                    response = bot.execute(message);
                    checkResponse(response);
                }else {
                    if (Pattern.matches("^.*\\.gif$", photos.getFilePath())){
                        SendAnimation animation = new SendAnimation(chatId,photos.getData())
                                .caption("Вы можете добавить/обновить схему проезда\n")
                                .replyMarkup(KeyboardsForAnswer.RETURN_KEYBOARD);
                        response = bot.execute(animation);
                        checkResponse(response);
                    }else if (Pattern.matches("^(?i).*\\.(jpg|jpeg|png|gif|bmp|tiff|webp|svg)$", photos.getFilePath())){
                        SendPhoto photoMessage = new SendPhoto(chatId, photos.getData())
                                .caption("Вы можете добавить/обновить схему проезда\n")
                                .replyMarkup(KeyboardsForAnswer.RETURN_KEYBOARD);
                        response = bot.execute(photoMessage);
                        checkResponse(response);
                    }else{
                        String prefix = photos.getFilePath().substring(photos.getFilePath().lastIndexOf("/")+1,photos.getFilePath().lastIndexOf(".")) ;
                        String suffix = photos.getFilePath().substring(photos.getFilePath().lastIndexOf(".")) ;
                        SendDocument sendDocument;
                        DiskFileItem fileItem;
                        try (InputStream in = new ByteArrayInputStream(photos.getData())) {
                            Path tempFile = Files.createTempFile(prefix, suffix);
                            Files.copy(in, tempFile, StandardCopyOption.REPLACE_EXISTING);
                            String mimeType = getMimeType(suffix);
                            if (mimeType == null){
                                mimeType = "application/octet-stream";
                            }
                            fileItem = new DiskFileItem(tempFile.toFile().getName(), mimeType, true, tempFile.toFile().getName(), (int)tempFile.toFile().length(), tempFile.getParent().toFile());
                            fileItem.getOutputStream().write(Files.readAllBytes(tempFile));

                            java.io.File newFile = new java.io.File(tempFile.toAbsolutePath().toString());
                            sendDocument = new SendDocument(chatId, newFile)
                                    .caption("Вы можете добавить/обновить схему проезда\n")
                                    .replyMarkup(KeyboardsForAnswer.RETURN_KEYBOARD);
                            response = bot.execute(sendDocument);
                            checkResponse(response);
                        } catch (Exception e) {
                            logger.error("Exception occurred: {}, Request Details: chatId = {} err: {}",
                                    "switchFunc->LoadShemaPhoto",chatId,e.getMessage());
                        }
                    }
                }
                saveUserPos(chatId,"Схема проезда","Информация о приюте");
            }
            case "Фото питомца" ->{
                Long petId = null;
                try {
                    petId = Long.parseLong(pos.substring(pos.indexOf("(") + 1, pos.indexOf(")")));
                }catch (Exception e){
                    logger.error("Exception occurred: {}, Request Details: param = {} err: {}",
                            "switchFunc->PetPhoto",pos,e.getMessage());
                }
                Photos photos = photoService.findPhotoByPetId(petId);

                if (photos==null){
                    message = new SendMessage(chatId, "Вы можете добавить фото питомца\n" +
                        "Текущее фото: ").replyMarkup(KeyboardsForAnswer.RETURN_KEYBOARD);
                    response = bot.execute(message);
                    checkResponse(response);
                }else {
                    if (Pattern.matches("^.*\\.gif$", photos.getFilePath())){
                        SendAnimation animation = new SendAnimation(chatId,photos.getData())
                                .caption("Вы можете добавить/обновить фото питомца\n")
                                .replyMarkup(KeyboardsForAnswer.RETURN_KEYBOARD);
                        response = bot.execute(animation);
                        checkResponse(response);
                    }else if (Pattern.matches("^(?i).*\\.(jpg|jpeg|png|gif|bmp|tiff|webp|svg)$", photos.getFilePath())){
                        SendPhoto photoMessage = new SendPhoto(chatId, photos.getData())
                                .caption("Вы можете добавить/обновить фото питомца\n")
                                .replyMarkup(KeyboardsForAnswer.RETURN_KEYBOARD);
                        response = bot.execute(photoMessage);
                        checkResponse(response);
                    }else{
                        String prefix = photos.getFilePath().substring(photos.getFilePath().lastIndexOf("/")+1,photos.getFilePath().lastIndexOf(".")) ;
                        String suffix = photos.getFilePath().substring(photos.getFilePath().lastIndexOf(".")) ;
                        SendDocument sendDocument;
                        DiskFileItem fileItem;
                        try (InputStream in = new ByteArrayInputStream(photos.getData())) {
                            Path tempFile = Files.createTempFile(prefix, suffix);
                            Files.copy(in, tempFile, StandardCopyOption.REPLACE_EXISTING);
                            String mimeType = getMimeType(suffix);
                            if (mimeType == null){
                                mimeType = "application/octet-stream";
                            }
                            fileItem = new DiskFileItem(tempFile.toFile().getName(), mimeType, true, tempFile.toFile().getName(), (int)tempFile.toFile().length(), tempFile.getParent().toFile());
                            fileItem.getOutputStream().write(Files.readAllBytes(tempFile));

                            java.io.File newFile = new java.io.File(tempFile.toAbsolutePath().toString());
                            sendDocument = new SendDocument(chatId, newFile)
                                    .caption("Вы можете добавить/обновить фото питомца\n")
                                    .replyMarkup(KeyboardsForAnswer.RETURN_KEYBOARD);
                            response = bot.execute(sendDocument);
                            checkResponse(response);
                        } catch (Exception e) {
                            logger.error("Exception occurred: {}, Request Details: petId = {} err: {}",
                                    "switchFunc->LoadPetPhoto",petId,e.getMessage());
                        }
                    }
                }
                saveUserPos(chatId,"Фото питомца",pos);
            }
            case "Тип питомца" ->{
                Long petId = null;
                try {
                    petId = Long.parseLong(pos.substring(pos.indexOf("(") + 1, pos.indexOf(")")));
                }catch (Exception e){
                    logger.error("Exception occurred: {}, Request Details: param = {} err: {}",
                            "switchFunc->PetType",pos,e.getMessage());
                }

                Pet pet = petService.findPetBypetIdOrName(petId,chatId);

                PetTypeDict petTypeDict = petTypeDictSevice.getTypeById(pet.getPetTypeId());

                String typeName = "";

                if (petTypeDict!=null){
                    typeName = petTypeDict.getName();
                }

                List<PetTypeDict> petTypeDictLst = petTypeDictSevice.getAllActiveTypes();

                if (petTypeDictLst.isEmpty()){
                    String messageText = EmojiParser.parseToUnicode(messageService.findById("noPetDictTypes").getText());
                    message = new SendMessage(chatId, messageText).replyMarkup(KeyboardsForAnswer.NO_INFO_KEYBOARD_CLNT);
                    saveUserPos(chatId,"Тип питомца",pos);
                }
                else {
                    ReplyKeyboardMarkup propsKeyboard = new ReplyKeyboardMarkup("").resizeKeyboard(true)
                            .oneTimeKeyboard(false);

                    for (PetTypeDict type : petTypeDictLst) {
                        propsKeyboard.addRow(new KeyboardButton(type.getName()));
                    }
                    propsKeyboard.addRow(new KeyboardButton("Назад"));
                    message = new SendMessage(chatId, "Выберите тип питомца\n" +
                            "Текущий тип: "+typeName).replyMarkup(propsKeyboard);
                }
                response = bot.execute(message);
                checkResponse(response);
                saveUserPos(chatId,"Тип питомца",pos);
            }
            case "Назад","Обновить" ->{
                switchFunc(prevPos,user,update,chatId,userPos);
            }
            default -> {
                User selectedShlt;
                AnimalShelterProps animalShelterProps;
                PropertyDict shltProp = propertyDictService.findByNameAndEntity(param, TelegramBotConsts.shelt);
                PropertyDict petShltProp = propertyDictService.findByNameAndEntity(param, TelegramBotConsts.pet);
                selectedShlt = userService.findByUserName(param);
                Long petId=null;
                Pet pet;
                PetTypeDict petTypeDict = petTypeDictSevice.getTypeByName(param);
                try{
                    petId =  Long.parseLong(param.substring(param.indexOf("(")+1,param.indexOf(")")));
                }catch (RuntimeException e){
                    logger.error("Exception occurred: {}, Request Details: param = {} chatId = {} err: {}",
                            "switchFunc->Default",param,chatId,e.getMessage());
                }
                pet = petService.findPetBypetIdOrName(petId,chatId);
                if (pos.equals("Все питомцы приюта")||pet!=null){
                    getShltPropsMenu(chatId, param,"Все питомцы приюта",TelegramBotConsts.pet);
                }
                else if (pos.equals("Имя питомца")){
                    if (Pattern.matches("^[a-zA-Zа-яА-ЯёЁ\\- ]+$", update.message().text())) {
                        petId = null;
                        try{
                            petId = Long.parseLong(prevPos.substring(prevPos.indexOf("(") + 1, prevPos.indexOf(")")));
                        }catch (Exception e){
                            logger.error("Exception occurred: {}, Request Details: param = {} err: {}",
                                    "switchFunc->PetNameIns",pos,e.getMessage());
                        }
                        pet = petService.findPetBypetIdOrName(petId, chatId);
                        pet.setName(update.message().text());
                        petService.addPet(pet);

                        getShltPropsMenu(chatId, pet.getName() + "(" + petId + ")", "Все питомцы приюта", TelegramBotConsts.pet);
                    }else {
                        message = new SendMessage(chatId, "Имя содержит недопустимые символы").replyMarkup(KeyboardsForAnswer.RETURN_KEYBOARD);
                        response = bot.execute(message);
                        checkResponse(response);
                    }
                }
                else if (pos.equals("Возраст питомца")){
                    if (Pattern.matches("^[a-zA-Zа-яА-ЯёЁ0-9 ]+$", update.message().text())){
                        petId = null;
                        try {
                            petId = Long.parseLong(prevPos.substring(prevPos.indexOf("(") + 1, prevPos.indexOf(")")));
                        }catch(Exception e){
                            logger.error("Exception occurred: {}, Request Details: param = {} err: {}",
                                    "switchFunc->PetAgeIns",prevPos,e.getMessage());
                        }
                        pet = petService.findPetBypetIdOrName(petId,chatId);
                        pet.setAge(update.message().text());
                        petService.addPet(pet);
                        getShltPropsMenu(chatId, pet.getName() + "(" + petId + ")", "Все питомцы приюта", TelegramBotConsts.pet);
                    }
                    else{
                        message = new SendMessage(chatId, "Возраст содержит недопустимые символы").replyMarkup(KeyboardsForAnswer.RETURN_KEYBOARD);
                        response = bot.execute(message);
                        checkResponse(response);
                    }
                }
                else if (pos.equals("Фото питомца")){
                    petId = null;
                    try {
                        petId = Long.parseLong(prevPos.substring(prevPos.indexOf("(") + 1, prevPos.indexOf(")")));
                    }catch(Exception e){
                        logger.error("Exception occurred: {}, Request Details: param = {} err: {}",
                                "switchFunc->PetPhotoIns",prevPos,e.getMessage());
                    }
                    pet = petService.findPetBypetIdOrName(petId,chatId);

                    var mesPhoto = update.message().photo();
                    var mesFile = update.message().document();

                    String fileId = "";
                    if (mesPhoto!=null){
                        fileId = mesPhoto[mesPhoto.length-1].fileId();
                    }else if (mesFile!=null){
                        fileId = mesFile.fileId();
                    }else{
                        switchFunc(prevPos, user, update, chatId, userPos);
                    }
                    GetFile getFileRequest = new GetFile(fileId);
                    GetFileResponse getFileResponse = bot.execute(getFileRequest);
                    File file = getFileResponse.file();
                    if (file!=null) {
                        try {
                            photoService.uploadPhoto(petId, null, file, bot.getToken());
                        } catch (IOException e) {
                            logger.error("Exception occurred: {}, Request Details: petId = {} chatId = {} err: {}",
                                    "uploadPetPhoto",petId,null,e.getMessage());
                        }
                    }
                    getShltPropsMenu(chatId, pet.getName() + "(" + petId + ")", "Все питомцы приюта", TelegramBotConsts.pet);

                }
                else if (pos.equals("Схема проезда")){
                    var mesPhoto = update.message().photo();
                    var mesFile = update.message().document();

                    String fileId = "";
                    if (mesPhoto!=null){
                        fileId = mesPhoto[mesPhoto.length-1].fileId();
                    }else if (mesFile!=null){
                        fileId = mesFile.fileId();
                    }else{
                        switchFunc(prevPos, user, update, chatId, userPos);
                    }
                    GetFile getFileRequest = new GetFile(fileId);
                    GetFileResponse getFileResponse = bot.execute(getFileRequest);
                    File file = getFileResponse.file();
                    if (file!=null) {
                        try {
                            photoService.uploadPhoto(null, chatId, file, bot.getToken());
                        } catch (IOException e) {
                            logger.error("Exception occurred: {}, Request Details: petId = {} chatId = {} err: {}",
                                    "uploadShemaPhoto",null,chatId,e.getMessage());
                        }
                    }
                    switchFunc(prevPos, user, update, chatId, userPos);
                }
                else if (pos.equals("Проверить номер")) {
                    if (Pattern.matches("\\+7-\\d{3}-\\d{3}-\\d{2}-\\d{2}", update.message().text())) {
                        user.setContacts(update.message().text());
                        userService.addUser(user);
                        switchFunc(prevPos, user, update, chatId, userPos);
                    } else {
                        message = new SendMessage(chatId, "Строка не соответствует формату +7-9**-***-**-**").replyMarkup(KeyboardsForAnswer.RETURN_KEYBOARD);
                        response = bot.execute(message);
                        checkResponse(response);
                        saveUserPos(chatId, "Проверить номер", "/start");
                    }
                } else if (petTypeDict!=null) {
                    petId = null;
                    try {
                        petId = Long.parseLong(prevPos.substring(prevPos.indexOf("(") + 1, prevPos.indexOf(")")));
                    }catch(Exception e){
                        logger.error("Exception occurred: {}, Request Details: param = {} err: {}",
                                "switchFunc->PetTypeIns",prevPos,e.getMessage());
                    }
                    pet = petService.findPetBypetIdOrName(petId,chatId);
                    pet.setPetTypeId(petTypeDict.getPetTypeId());
                    petService.addPet(pet);
                    getShltPropsMenu(chatId, pet.getName() + "(" + petId + ")", "Все питомцы приюта", TelegramBotConsts.pet);
                } else if (shltProp != null){
                    if(user.getEntityId() == TelegramBotConsts.shelt){
                        String nowVal = "";
                        animalShelterProps = animalShelterPropsService.getUserProp(shltProp.getPropId(), chatId);
                        if (animalShelterProps != null) {
                            nowVal = animalShelterProps.getPropVal();
                        }
                        message = new SendMessage(chatId, "Введите информацию \"" + shltProp.getName() + "\".\n" +
                                "Текущая информация: " + nowVal)
                                .replyMarkup(KeyboardsForAnswer.RETURN_KEYBOARD);
                    } else {
                        String nowVal = "";
                        animalShelterProps = animalShelterPropsService.getUserProp(shltProp.getPropId(), user.getShlId());
                        if (animalShelterProps != null) {
                            nowVal = animalShelterProps.getPropVal();
                        }
                        message = new SendMessage(chatId, nowVal);
                    }

                    response = bot.execute(message);
                    checkResponse(response);
                    saveUserPos(chatId, param, pos);

                } else if (petShltProp!=null){
                    if(user.getEntityId() == TelegramBotConsts.shelt){
                        String nowVal = "";
                        animalShelterProps = animalShelterPropsService.getUserProp(petShltProp.getPropId(),chatId);
                        if(animalShelterProps!=null){
                            nowVal = animalShelterProps.getPropVal();
                        }
                        message = new SendMessage(chatId, "Введите информацию \""+petShltProp.getName()+"\".\n" +
                                "Текущая информация: "+nowVal)
                                .replyMarkup(KeyboardsForAnswer.RETURN_KEYBOARD);
                    }else{
                        String nowVal = "";
                        animalShelterProps = animalShelterPropsService.getUserProp(petShltProp.getPropId(),user.getShlId());
                        if(animalShelterProps!=null){
                            nowVal = animalShelterProps.getPropVal();
                        }
                        message = new SendMessage(chatId, nowVal);
                    }

                    response = bot.execute(message);
                    checkResponse(response);
                    saveUserPos(chatId,param,pos);
                }else if (selectedShlt != null) {
                    user.setShlId(selectedShlt.getChatId());
                    userService.addUser(user);
                    getClientShltMenu(chatId, user.getShlId());
                } else {
                    shltProp = propertyDictService.findByNameAndEntity(pos, TelegramBotConsts.shelt);
                    petShltProp = propertyDictService.findByNameAndEntity(pos, TelegramBotConsts.pet);
                    if (shltProp!=null){
                        animalShelterProps = animalShelterPropsService.getUserProp(shltProp.getPropId(),chatId);
                        if (animalShelterProps!=null){
                            animalShelterProps.setDateTo(LocalDateTime.now());
                            animalShelterPropsService.addShelterProp(animalShelterProps);
                        }
                        animalShelterProps = AnimalShelterProps.builder()
                                .chatId(chatId)
                                .propId(shltProp.getPropId())
                                .propVal(update.message().text())
                                .build();
                        animalShelterPropsService.addShelterProp(animalShelterProps);
                        getShltPropsMenu(chatId,"Информация о приюте","/start",TelegramBotConsts.shelt);
                    } else if (petShltProp!=null) {
                        petId = null;
                        try {
                            petId = Long.parseLong(prevPos.substring(prevPos.indexOf("(") + 1, prevPos.indexOf(")")));
                        }catch (Exception e){
                            logger.error("Exception occurred: {}, Request Details: param = {} err: {}",
                                    "switchFunc->petShltProp",prevPos,e.getMessage());
                        }
                        pet = petService.findPetBypetIdOrName(petId,chatId);
                        animalShelterProps = animalShelterPropsService.getUserProp(petShltProp.getPropId(),chatId);
                        if (animalShelterProps!=null){
                            animalShelterProps.setDateTo(LocalDateTime.now());
                            animalShelterPropsService.addShelterProp(animalShelterProps);
                        }
                        animalShelterProps = AnimalShelterProps.builder()
                                .chatId(chatId)
                                .propId(petShltProp.getPropId())
                                .propVal(update.message().text())
                                .build();
                        animalShelterPropsService.addShelterProp(animalShelterProps);
                        getShltPropsMenu(chatId, pet.getName() + "(" + petId + ")", "Все питомцы приюта", TelegramBotConsts.pet);
                    } else {
                        if (user.getEntityId() == TelegramBotConsts.user) {
                            message = new SendMessage(chatId, "Вы можете позвать волонтера").replyMarkup(KeyboardsForAnswer.SUPPORT_KEYBOARD);
                        } else {
                            message = new SendMessage(chatId, "Данная функция не поддерживается");
                        }

                        response = bot.execute(message);
                        checkResponse(response);
                    }
                }
            }
        }
    }
}