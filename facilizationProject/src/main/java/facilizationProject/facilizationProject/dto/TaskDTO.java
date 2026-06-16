package facilizationProject.facilizationProject.dto;

import facilizationProject.facilizationProject.enums.PriorityEnum;
import facilizationProject.facilizationProject.enums.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {

   private Long id;

   @NotBlank
   @Size(min = 3, max = 100,
            message = "Title should be between 3 till 100 charachters")
   private String title;

   private String description;

   @NotNull(message = "Priority is required")
   private PriorityEnum priority;

   @NotNull(message = "Status is required")
   private StatusEnum status;

   private LocalDateTime createdAt;

   private LocalDate dueDate;

    @NotNull(message = "Project ID is required")
    private ProjectDTO projectDTO;

    private UserDTO userDTO;

   public TaskDTO(Long id)
   {
       this.id = id;
   }
}
