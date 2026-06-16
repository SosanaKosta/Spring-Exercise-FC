package facilizationProject.facilizationProject.exception.config;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorFormat {
    private LocalDateTime localDateTime;
    private String error;
}
