package Mikhail_gnome.com.github.contactApp.exception.handler;

import Mikhail_gnome.com.github.contactApp.common.util.ServerResponseHelper;
import Mikhail_gnome.com.github.contactApp.model.ServerResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ServerResponse<Void>> handleValidationException(
            Exception ex
    ) {
        return ServerResponseHelper.conflict(null, Collections.singletonList(ex.getMessage()));
    }
}
