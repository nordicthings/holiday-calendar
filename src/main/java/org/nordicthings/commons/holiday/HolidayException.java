package org.nordicthings.commons.holiday;

/**
 * Is thrown if a holiday-based calculation fails
 */
public class HolidayException extends RuntimeException {
    public HolidayException(String message) {
        super(message);
    }
}
