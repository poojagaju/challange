package com.dws.challenge.model;

import lombok.Data;

/**
 * Represents error information with a code and a message.
 */
@Data
public class ApiError {

    private String code;
    private String message;

    /**
     * Sets the error code.
     * @param code the error code
     * @return the current ApiError instance for method chaining
     */
    public ApiError code(String code) {
        this.code = code;
        return this;
    }

    /**
     * Sets the error message.
     * @param message the error message
     * @return the current ApiError instance for method chaining
     */
    public ApiError message(String message) {
        this.message = message;
        return this;
    }
}
