package pro.sky.petpartnersbot.service.utils;

import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;

public class KeyboardsForAnswer {
    public final static Keyboard START_KEYBOARD = new ReplyKeyboardMarkup(new KeyboardButton("Приют"))
            .addRow(new KeyboardButton("Клиент"))
            .resizeKeyboard(true)
            .oneTimeKeyboard(false);

    public final static Keyboard RETURN_KEYBOARD = new ReplyKeyboardMarkup(new KeyboardButton("Назад"))
            .resizeKeyboard(true)
            .oneTimeKeyboard(false);

    public final static Keyboard NO_INFO_KEYBOARD = new ReplyKeyboardMarkup(new KeyboardButton("Обновить"))
            .addRow(new KeyboardButton("Удалить учетную запись"))
            .resizeKeyboard(true)
            .oneTimeKeyboard(false);

    public final static Keyboard NO_INFO_KEYBOARD_CLNT = new ReplyKeyboardMarkup(new KeyboardButton("Обновить"))
            .addRow(new KeyboardButton("Мой номер телефона"))
            .addRow(new KeyboardButton("Выбрать другой приют"))
            .addRow(new KeyboardButton("Удалить учетную запись"))
            .resizeKeyboard(true)
            .oneTimeKeyboard(false);

    public final static Keyboard SUPPORT_KEYBOARD = new ReplyKeyboardMarkup(new KeyboardButton("Позвать волонтера"))
            .addRow(new KeyboardButton("Назад"))
            .resizeKeyboard(true)
            .oneTimeKeyboard(false);
}
