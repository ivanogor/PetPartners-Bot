package pro.sky.petpartnersbot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.DeleteMyCommands;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурационный класс для настройки Telegram бота.
 * Этот класс создает и настраивает экземпляр TelegramBot, используя токен, полученный из свойств приложения.
 */
@Data
@Configuration
public class TelegramBotConfiguration {
    /**
     * Токен Telegram бота, полученный из свойств приложения.
     */
    @Value("${telegram.bot.token}")
    private String token;

    /**
     * Создает и настраивает экземпляр TelegramBot.
     * Удаляет все команды бота перед возвратом экземпляра.
     *
     * @return Настроенный экземпляр TelegramBot.
     */
    @Bean
    public TelegramBot telegramBot() {
        TelegramBot bot = new TelegramBot(token);
        bot.execute(new DeleteMyCommands());
        return bot;
    }
}