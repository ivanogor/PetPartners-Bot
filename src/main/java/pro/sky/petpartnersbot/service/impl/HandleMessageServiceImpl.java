package pro.sky.petpartnersbot.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.petpartnersbot.entity.User;
import pro.sky.petpartnersbot.service.HandleMessageService;
import pro.sky.petpartnersbot.service.utils.KeyboardsForAnswer;

import java.util.Objects;

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
    private final AnimalShelterPropsServiceImpl animalShelterPropsService;
    private boolean isDog;

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
        SendMessage message;
        SendResponse response;
        User foundedUser = userService.findById(chatId);
        //Анализ выбранной кнопки меню или команды /start
        switch (updateText) {
            case "/start" -> {
                //Если пользователь впревые использует чат, то добавляем его в БД
                if (Objects.isNull(foundedUser)) {
                    User newUser = User.builder()
                            .chatId(chatId)
                            .userName((update.message().chat().username()))
                            .build();
                    userService.addUser(newUser);
                    String messageText = messageService.findById("welcomeMessage").getText();
                    message = new SendMessage(chatId, messageText).replyMarkup(KeyboardsForAnswer.START_KEYBOARD);
                } else {
                    message = new SendMessage(chatId, "Выберите приют").replyMarkup(KeyboardsForAnswer.START_KEYBOARD);
                }
                //Проверка выполнения отправки сообщения
                response = bot.execute(message);
                checkResponse(response);
            }
            case "Приют для кошек" -> {
                isDog = false;
                message = new SendMessage(chatId, "Приют для кошек").replyMarkup(KeyboardsForAnswer.MAIN_KEYBOARD);
                response = bot.execute(message);
                //Проверка выполнения отправки сообщения
                checkResponse(response);
            }
            case "Приют для собак" -> {
                isDog = true;
                message = new SendMessage(chatId, "Приют для собак").replyMarkup(KeyboardsForAnswer.MAIN_KEYBOARD);
                response = bot.execute(message);
                //Проверка выполнения отправки сообщения
                checkResponse(response);
            }
            case "Как взять животное из приюта?" -> {
                //Добавить инфу из приюта из сущности***********
                message = new SendMessage(chatId, "Тест1");
                response = bot.execute(message);
                //Проверка выполнения отправки сообщения
                checkResponse(response);
            }
            case "Информация о приюте" -> {
                message = new SendMessage(chatId, "Выберите меню:").replyMarkup(KeyboardsForAnswer.SHELTER_KEYBOARD);
                response = bot.execute(message);
                checkResponse(response);
            }
            case "Прислать отчет о питомце" -> {
                //Добавить инфу в отчет в сущность***********
                message = new SendMessage(chatId, "Тест3");
                response = bot.execute(message);
                //Проверка выполнения отправки сообщения

                checkResponse(response);
            }
            case "Расписание работы" -> {
                if (isDog){
                    message = new SendMessage(chatId, messageService.findById("scheduleDogShelter").getText()).replyMarkup(KeyboardsForAnswer.SHELTER_KEYBOARD);
                }
                else {
                    message = new SendMessage(chatId, messageService.findById("scheduleCatShelter").getText()).replyMarkup(KeyboardsForAnswer.SHELTER_KEYBOARD);
                }
                response = bot.execute(message);
                checkResponse(response);
            }

            case "Описание приюта" -> {
                if (isDog){
                    message = new SendMessage(chatId, messageService.findById("infoDogShelter").getText()).replyMarkup(KeyboardsForAnswer.SHELTER_KEYBOARD);
                }
                else {
                    message = new SendMessage(chatId, messageService.findById("infoCatShelter").getText()).replyMarkup(KeyboardsForAnswer.SHELTER_KEYBOARD);
                }
                response = bot.execute(message);
                checkResponse(response);
            }
            case "Выбрать другой приют" -> {
                message = new SendMessage(chatId, "Выберите приют").replyMarkup(KeyboardsForAnswer.START_KEYBOARD);
                response = bot.execute(message);
                checkResponse(response);
            }
            case "Позвать волонтера" -> {
                //Пока отправляю жесткий ид приюта по хорошему нужно исходя из выбора юзера
                bot.execute(new SendMessage(animalShelterPropsService.getVolunteerChat(1L)
                        ,"Пользователь \ntg://openmessage?user_id="+chatId+"\nhttps://web.telegram.org/a/#"+
                        chatId+"\nпросит связаться с волонтером"));

                message = new SendMessage(chatId, "Запрос отправлен. С вами свяжутся в течении 10-15 минут");
                response = bot.execute(message);
                //Проверка выполнения отправки сообщения

                checkResponse(response);
            }
            case "/delete" ->
                //Если пользователь уже добавлен, то удаляю его. Это для теста
                    userService.deleteUser(chatId);

            default -> {
                message = new SendMessage(chatId, "Вы можете позвать волонтера").replyMarkup(KeyboardsForAnswer.SUPPORT_KEYBOARD);
                response = bot.execute(message);
                //Проверка выполнения отправки сообщения
                checkResponse(response);
            }
        }
    }

    //Метод проверки отправки сообщения
    private void checkResponse(SendResponse response) {
        if (!response.isOk()) {
            logger.error("Response isn't correct. Error code: {}", response.errorCode());
        }
    }


}