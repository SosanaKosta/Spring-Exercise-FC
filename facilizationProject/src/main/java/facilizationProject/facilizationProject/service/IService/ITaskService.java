package facilizationProject.facilizationProject.service.IService;

import facilizationProject.facilizationProject.dto.TaskDTO;
import facilizationProject.facilizationProject.enums.StatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITaskService {

    TaskDTO createTask(Long projectId, TaskDTO dto);

    TaskDTO getTaskById(Long id);

    Page<TaskDTO> getTasksByProjects(Long projectId, StatusEnum statusEnum, Pageable pageable);

    TaskDTO updateTask(Long id, TaskDTO taskDTO);

    void deleteTask(Long id);

    List<TaskDTO> getTaskDueToday();

    List<TaskDTO> getAllTasksByUser(Long userId);

}
