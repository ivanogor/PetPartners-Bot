package pro.sky.petpartnersbot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.petpartnersbot.service.impl.HandleMessageServiceImpl;

import java.util.List;

/**
 * Слушатель обновлений для Telegram бота.
 * Этот класс отвечает за обработку входящих обновлений от Telegram API и передачу их в соответствующий сервис для обработки.
 */
@RequiredArgsConstructor
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final TelegramBot telegramBot;
    private final HandleMessageServiceImpl handler;

    /**
     * Инициализация слушателя обновлений после создания бина.
     * Устанавливает этот слушатель как обработчик обновлений для Telegram бота.
     */
    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    /**
     * Обрабатывает список входящих обновлений от Telegram API.
     * Каждое обновление передается в сервис для дальнейшей обработки.
     *
     * @param updates Список обновлений от Telegram API.
     * @return Код подтверждения обработки всех обновлений.
     */
    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            if (update != null) {
                handler.handleMessage(update);
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}