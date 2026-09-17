package com.vitor.api.exceptions;

import java.time.LocalDateTime;

public record ExceptionDTO(
        LocalDateTime timeStamp,
        int errorCode,
        String error,
        String message,
        String url
) {
}
