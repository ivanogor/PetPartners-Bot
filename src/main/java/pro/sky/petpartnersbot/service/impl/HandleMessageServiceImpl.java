package pro.sky.petpartnersbot.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.petpartnersbot.configuration.TelegramBotConsts;
import pro.sky.petpartnersbot.entity.AnimalShelterProps;
import pro.sky.petpartnersbot.entity.PropertyDict;
import pro.sky.petpartnersbot.entity.User;
import pro.sky.petpartnersbot.entity.UserPos;
import pro.sky.petpartnersbot.service.HandleMessageService;
import pro.sky.petpartnersbot.service.utils.KeyboardsForAnswer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import static com.pengrad.telegrambot.model.request.ParseMode.HTML;

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
    private final ShelterRoadMapServiceImpl shelterRoadMapService;

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
            //Переход к анализу сообщений
            processText(updateText, chatId, update);
        }
    }

    /**
     * Анализирует текст сообщения и выполняет соответствующие действия.
     *
     * @param updateText Текст сообщения.
     * @param chatId     Идентификатор чата.
     */
    //Метод анализа сообщений
    private void processText(String updateText, Long chatId, Update update) {
        logger.info("Was invoked switching message with text method");

        UserPos userPos = userPosService.findByChatId(chatId);
        User foundedUser = userService.findById(chatId);
        switchFunc(updateText, foundedUser, update, chatId, userPos);

    }

    private void getShltPropsMenu(Long chatId, String pos) {
        SendMessage message;
        SendResponse response;
        List<PropertyDict> shltProps = propertyDictService.findByEntity(TelegramBotConsts.shelt);
        if (shltProps.isEmpty()) {
            message = new SendMessage(chatId, "Действующих разделов о приюте " +
                    "не найдено свяжитесь с администратором бота").replyMarkup(new ReplyKeyboardMarkup("Обновить").resizeKeyboard(true)
                    .oneTimeKeyboard(false));
        } else {
            String messageText = messageService.findById("addShltInfo").getText();

            ReplyKeyboardMarkup propsKeyboard = new ReplyKeyboardMarkup("").resizeKeyboard(true)
                    .oneTimeKeyboard(false);

            for (PropertyDict shltProp : shltProps) {
                propsKeyboard.addRow(new KeyboardButton(shltProp.getName()));
            }

            propsKeyboard.addRow(new KeyboardButton("Удалить учетную запись"));
            message = new SendMessage(chatId, messageText).replyMarkup(propsKeyboard);
            saveUserPos(chatId, "Приют", pos);
        }

        //Проверка выполнения отправки сообщения
        response = bot.execute(message);
        checkResponse(response);
    }

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

            propsKeyboard.addRow(new KeyboardButton("Удалить учетную запись"));
            message = new SendMessage(chatId, messageText).replyMarkup(propsKeyboard);
            saveUserPos(chatId, pos, pos);
        }

        //Проверка выполнения отправки сообщения
        response = bot.execute(message);
        checkResponse(response);
    }

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

        //Проверка выполнения отправки сообщения
        response = bot.execute(message);
        checkResponse(response);
    }

    private void saveUserPos(Long chatId, String pos, String prevPos) {
        UserPos userPos = UserPos.builder()
                .chatId(chatId)
                .pos(pos)
                .prevPos(prevPos)
                .build();
        userPosService.saveUserPos(userPos);
    }

    //Метод проверки отправки сообщения
    private void checkResponse(SendResponse response) {
        if (!response.isOk()) {
            logger.error("Response isn't correct. Error code: {}", response.errorCode());
        }
    }

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
        boolean chekAnyShltExist = userService.checkIfAnyExistByEnt(TelegramBotConsts.shelt) == 0 ? false : true;
        if (pos.equals("Схема проезда") && param == null && user.getEntityId() == TelegramBotConsts.shelt) {
            AnimalShelterProps animalShelterProps;
            PropertyDict shltProp;
            shltProp = propertyDictService.findByNameAndEntity("Схема проезда", TelegramBotConsts.shelt);
            animalShelterProps = animalShelterPropsService.getUserProp(shltProp.getPropId(), chatId);
            if (animalShelterProps != null) {
                animalShelterProps.setDateTo(LocalDateTime.now());
                animalShelterPropsService.addShelterProp(animalShelterProps);
            }
            animalShelterProps = AnimalShelterProps.builder()
                    .chatId(chatId)
                    .propId(shltProp.getPropId())
                    .propVal(pos)
                    .roadMapId(shelterRoadMapService.switchAct(bot, update, user))
                    .build();
            animalShelterPropsService.addShelterProp(animalShelterProps);
            getShltPropsMenu(chatId, "/start");
            saveUserPos(chatId, "/start", pos);
        } else if (pos.equals("Схема проезда") && user.getEntityId() == TelegramBotConsts.user) {
            shelterRoadMapService.getRoadMap(user);
        } else {
            switch (param) {
                case "/start" -> {
                    //Если пользователь впревые использует чат, то спрашиваем кто он
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
                    } else if (user.getEntityId() == TelegramBotConsts.shelt) {
                        getShltPropsMenu(chatId, "/start");
                    }
                    //Проверка выполнения отправки сообщения
                }
                case "Приют" -> {
                    //Если пользователь впревые использует чат, то добавляем его в БД
                    if (Objects.isNull(user)) {
                        User newUser = User.builder()
                                .chatId(chatId)
                                .userName((update.message().chat().username()))
                                .entityId(TelegramBotConsts.shelt)
                                .build();
                        userService.addUser(newUser);
                    }
                    getShltPropsMenu(chatId, "/start");
                }
                case "Клиент" -> {
                    //Если пользователь впревые использует чат, то добавляем его в БД если есть зареганный приют
                    User newUser = User.builder()
                            .chatId(chatId)
                            .entityId(TelegramBotConsts.user)
                            .userName((update.message().chat().username()))
                            .build();
                    userService.addUser(newUser);
                    getClientMenu(chatId, "/start");
                }
                case "Позвать волонтера" -> {
                    //Пока отправляю жесткий ид приюта по хорошему нужно исходя из выбора юзера
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
                    //Проверка выполнения отправки сообщения

                    checkResponse(response);
                    saveUserPos(chatId, "/start", "/start");
                }
                case "Удалить учетную запись" -> {
                    //Если пользователь уже добавлен, то удаляю его. Это для теста
                    userService.deleteUser(chatId);
                    message = new SendMessage(chatId, "Учетная запись удалена").replyMarkup(KeyboardsForAnswer.START_KEYBOARD);
                    response = bot.execute(message);
                    checkResponse(response);
                }
                case "Выбрать другой приют" -> {
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
                case "Назад", "Обновить" -> {
                    switchFunc(prevPos, user, update, chatId, userPos);
                }
                default -> {
                    PropertyDict shltProp;
                    User selectedShlt;
                    AnimalShelterProps animalShelterProps;
                    shltProp = propertyDictService.findByNameAndEntity(param, TelegramBotConsts.shelt);
                    selectedShlt = userService.findByUserName(param);
                    if (pos.equals("Проверить номер")) {
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
                    } else if (shltProp != null) {
                        if (user.getEntityId() == TelegramBotConsts.shelt) {
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

                    } else if (selectedShlt != null) {
                        user.setShlId(selectedShlt.getChatId());
                        userService.addUser(user);
                        getClientShltMenu(chatId, user.getShlId());
                    } else {
                        shltProp = propertyDictService.findByNameAndEntity(pos, user.getEntityId());
                        if (shltProp != null) {
                            animalShelterProps = animalShelterPropsService.getUserProp(shltProp.getPropId(), chatId);
                            if (animalShelterProps != null) {
                                animalShelterProps.setDateTo(LocalDateTime.now());
                                animalShelterPropsService.addShelterProp(animalShelterProps);
                            }
                            animalShelterProps = AnimalShelterProps.builder()
                                    .chatId(chatId)
                                    .propId(shltProp.getPropId())
                                    .propVal(update.message().text())
                                    .build();
                            animalShelterPropsService.addShelterProp(animalShelterProps);
                            getShltPropsMenu(chatId, "/start");
                        } else {
                            if (user.getEntityId() == TelegramBotConsts.user) {
                                message = new SendMessage(chatId, "Вы можете позвать волонтера").replyMarkup(KeyboardsForAnswer.SUPPORT_KEYBOARD);
                            } else {
                                message = new SendMessage(chatId, "Данная функция не поддерживается");
                            }

                            response = bot.execute(message);
                            //Проверка выполнения отправки сообщения
                            checkResponse(response);
                        }
                    }
                }
            }
        }
    }
}