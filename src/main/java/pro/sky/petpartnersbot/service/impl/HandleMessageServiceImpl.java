package pro.sky.petpartnersbot.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.petpartnersbot.entity.PetParent;
import pro.sky.petpartnersbot.service.HandleMessageService;

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
    private final PetParentServiceImpl parentService;

    /**
     * Обрабатывает входящее сообщение от Telegram API.
     * Если пользователь впервые использует чат, добавляет его в базу данных и переходит к анализу сообщений.
     * Иначе перенаправляет пользователя к выбору приюта.
     *
     * @param update Входящее обновление от Telegram API.
     */
    @Override
    public void handleMessage(Update update) {
        logger.info("Handling message");
        String updateText = update.message().text().toLowerCase();
        Long chatId = update.message().chat().id();
        if (parentService.findByChatId(chatId) == null) {
            parentService.addParent(new PetParent(1, chatId, update.message().chat().username(),
                    update.message().chat().firstName(), update.message().chat().lastName(),
                    "", false, false));
            switchText(updateText, chatId);
        } else {
            SendMessage message = new SendMessage(chatId, "Переход к выбору приюта <функционал в разработке>");
            SendResponse response = bot.execute(message);
            if (!response.isOk()) {
                logger.error("Response isn't correct. Error code: " + response.errorCode());
            }
            parentService.deleteParent(parentService.findByChatId(chatId).getId());
        }
    }

    /**
     * Анализирует текст сообщения и выполняет соответствующие действия.
     *
     * @param updateText Текст сообщения.
     * @param chatId     Идентификатор чата.
     */
    private void switchText(String updateText, Long chatId) {
        logger.info("Was invoked switching message with text method");
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup(
                new KeyboardButton("Узнать информацию о приюте"),
                new KeyboardButton("Как взять животное из приюта"),
                new KeyboardButton("Прислать отчет о питомце"),
                new KeyboardButton("Позвать волонтера")
        );
        keyboard.resizeKeyboard(true);
        keyboard.oneTimeKeyboard(false);
        keyboard.selective(false);

        switch (updateText) {
            case "/start":
                String messageText = messageService.findByType("welcomeMessage").getText();
                SendMessage message = new SendMessage(chatId, messageText).replyMarkup(keyboard);
                SendResponse response = bot.execute(message);
                if (!response.isOk()) {
                    logger.error("Response isn't correct. Error code: " + response.errorCode());
                }
                break;
        }
    }
}