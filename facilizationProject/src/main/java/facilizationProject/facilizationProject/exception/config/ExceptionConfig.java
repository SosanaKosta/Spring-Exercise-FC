package facilizationProject.facilizationProject.exception.config;

import facilizationProject.facilizationProject.exception.ResourceNotFoundException;
import facilizationProject.facilizationProject.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionConfig {

    private static <T extends RuntimeException> ErrorFormat extractErrorFormat(T exception){
        ErrorFormat errorFormat = new ErrorFormat();
        errorFormat.setError(exception.getMessage());
        errorFormat.setLocalDateTime(LocalDateTime.now());
        return errorFormat;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public ErrorFormat handleNotFoundException(ResourceNotFoundException notFoundException){
        return extractErrorFormat(notFoundException);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public ErrorFormat handleBadRequestException(BadRequestException badRequestException){
        return extractErrorFormat(badRequestException);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        Map<String, String> errorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
        return errorMap;
    }
}
