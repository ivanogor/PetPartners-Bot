package pro.sky.petpartnersbot.service.utils;

import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;

public class KeyboardsForAnswer {
    public final static Keyboard START_KEYBOARD = new ReplyKeyboardMarkup(new KeyboardButton("Приют для кошек"))
            .addRow(new KeyboardButton("Приют для собак"))
            .resizeKeyboard(true)
            .oneTimeKeyboard(false);

    public final static Keyboard MAIN_KEYBOARD = new ReplyKeyboardMarkup(new KeyboardButton("Информация о приюте"))
            .addRow(new KeyboardButton("Прислать отчет о питомце"))
            .addRow(new KeyboardButton("Позвать волонтера"))
            .resizeKeyboard(true)
            .oneTimeKeyboard(false);

    public final static Keyboard SHELTER_KEYBOARD = new ReplyKeyboardMarkup(new KeyboardButton("Описание приюта"))
            .addRow(new KeyboardButton("Расписание работы"))
            .addRow(new KeyboardButton("Схема проезда"))
            .addRow(new KeyboardButton("Контактные данные охраны"))
            .addRow(new KeyboardButton("Рекомендации техники безопасности"))
            .addRow(new KeyboardButton("Выбрать другой приют"))
            .addRow(new KeyboardButton("Позвать волонтера"))
            .resizeKeyboard(true)
            .oneTimeKeyboard(false);

    public final static Keyboard SUPPORT_KEYBOARD = new ReplyKeyboardMarkup(new KeyboardButton("Узнать информацию о приюте"))
            .addRow(new KeyboardButton("Позвать волонтера"))
            .resizeKeyboard(true)
            .oneTimeKeyboard(false);
}
