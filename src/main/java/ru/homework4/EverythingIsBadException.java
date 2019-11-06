package ru.homework4;

/**
 * не смог вспомнить какое исключение бросается если начал программа исполняет код, который, теоритически, никогда
 * не должен исполняться, по этому написал свое
 */
class EverythingIsBadException extends RuntimeException {
    EverythingIsBadException(String message) {
        super(message);
    }
}
