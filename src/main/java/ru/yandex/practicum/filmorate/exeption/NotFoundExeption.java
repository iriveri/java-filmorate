package ru.yandex.practicum.filmorate.exeption;

public class NotFoundExeption extends RuntimeException {

    public NotFoundExeption() {
    }

    public NotFoundExeption(final String message) {
        super(message);
    }

    public NotFoundExeption(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NotFoundExeption(final Throwable cause) {
        super(cause);
    }
}