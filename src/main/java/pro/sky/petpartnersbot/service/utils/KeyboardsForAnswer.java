package pro.sky.petpartnersbot.service.utils;

import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;

public class KeyboardsForAnswer {
    public final static Keyboard START_KEYBOARD = new ReplyKeyboardMarkup(new KeyboardButton("Приют для кошек"))
            .addRow(new KeyboardButton("Приют для собак"))
            .resizeKeyboard(true)
            .oneTimeKeyboard(false);

    public final static Keyboard MAIN_KEYBOARD = new ReplyKeyboardMarkup(new KeyboardButton("Узнать информацию о приюте"))
            .addRow(new KeyboardButton("Как взять животное из приюта"))
            .addRow(new KeyboardButton("Прислать отчет о питомце"))
            .addRow(new KeyboardButton("Позвать волонтера"))
            .resizeKeyboard(true)
            .oneTimeKeyboard(false);
}
