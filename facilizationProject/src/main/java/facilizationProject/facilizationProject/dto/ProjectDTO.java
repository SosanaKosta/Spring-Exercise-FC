package facilizationProject.facilizationProject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {

    private Long id;

    @NotBlank(message = "Project name is required")
    @Size(min = 3, max = 50,
            message = "Name should be betwen 3-50 charachters long")
    private String name;

    private String description;

    private LocalDateTime createdAt;

    private UserDTO ownerId;

    public ProjectDTO(Long id) {
        this.id = id;
    }
}
