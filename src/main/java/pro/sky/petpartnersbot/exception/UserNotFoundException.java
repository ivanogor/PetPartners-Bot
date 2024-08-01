package pro.sky.petpartnersbot.exception;

/**
 * Исключение, которое выбрасывается, когда пользователь не найден.
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}