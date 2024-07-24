package pro.sky.petpartnersbot.exception;

/**
 * Исключение, которое выбрасывается, когда сообщение не найдено.
 */
public class MessageNotFoundException extends RuntimeException {
    public MessageNotFoundException(String message) {
        super(message);
    }
}
