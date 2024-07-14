package pro.sky.petpartnersbot.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.petpartnersbot.entity.User;
import pro.sky.petpartnersbot.service.HandleMessageService;

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
    private final MessageServiceImpl messageService;
    private final UserServiceImpl userService;

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
            //Кнопки меню
            Keyboard keyboard = new ReplyKeyboardMarkup(new KeyboardButton("Узнать информацию о приюте"))
                    .addRow(new KeyboardButton("Как взять животное из приюта?"))
                    .addRow(new KeyboardButton("Прислать отчет о питомце"))
                    .addRow(new KeyboardButton("Позвать волонтера"))
                    .resizeKeyboard(true).oneTimeKeyboard(false);
            //Переход к анализу сообщений
            processText(updateText, chatId, keyboard, update);
        }
    }

    /**
     * Анализирует текст сообщения и выполняет соответствующие действия.
     *
     * @param updateText Текст сообщения.
     * @param chatId     Идентификатор чата.
     */
    //Метод анализа сообщений
    private void processText(String updateText, Long chatId, Keyboard keyboard, Update update) {
        logger.info("Was invoked swtching message with text method");
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
                            .build();
                    userService.addUser(newUser);
                    String messageText = messageService.findById("welcomeMessage").getText();
                    message = new SendMessage(chatId, messageText).replyMarkup(keyboard);
                    response = bot.execute(message);
                    //Проверка выполнения отправки сообщения
                    checkResponce(response);
                    //Иначе перенаправляем к выбору приюта
                } else {
                    message = new SendMessage(chatId,
                            "Выбор приюта <функционал в разработке>").replyMarkup(keyboard);
                    response = bot.execute(message);
                    //Проверка выполнения отправки сообщения
                    checkResponce(response);
                }
            }
            case "Узнать информацию о приюте" -> {
                //Добавить инфу из приюта из сущности***********
                message = new SendMessage(chatId, "Информация о приюте <функционал в разработке>");
                response = bot.execute(message);
                //Проверка выполнения отправки сообщения
                checkResponce(response);
            }
            case "Как взять животное из приюта?" -> {
                //Добавить инфу из приюта из сущности***********
                message = new SendMessage(chatId, "Как взять животное из приюта <функционал в разработке>");
                response = bot.execute(message);
                //Проверка выполнения отправки сообщения
                checkResponce(response);
            }
            case "Прислать отчет о питомце" -> {
                //Добавить инфу в отчет в сущность***********
                message = new SendMessage(chatId, "Прислать отчет о питомце <функционал в разработке>");
                response = bot.execute(message);
                //Проверка выполнения отправки сообщения
                checkResponce(response);
            }
            case "Позвать волонтера" -> {
                //Добавить инфу из приюта из сущности***********
                message = new SendMessage(chatId, "Позвать волонтера <функционал в разработке>");
                response = bot.execute(message);
                //Проверка выполнения отправки сообщения
                checkResponce(response);
            }
            case "/delete" -> {
                //Если пользователь уже добавлен, то удаляю его. Это для теста
                userService.deleteUser(chatId);
            }
            default -> {
                message = new SendMessage(chatId, "Выберите необходимый пункт в меню");
                response = bot.execute(message);
                //Проверка выполнения отправки сообщения
                checkResponce(response);
            }
        }
    }

    //Метод проверки отправки сообщения
    private void checkResponce(SendResponse response) {
        if (!response.isOk()) {
            logger.error("Response isn't correct. Error code: " + response.errorCode());
        }
    }
}