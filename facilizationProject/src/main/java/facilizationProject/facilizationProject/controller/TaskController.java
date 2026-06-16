package facilizationProject.facilizationProject.controller;

import facilizationProject.facilizationProject.dto.TaskDTO;
import facilizationProject.facilizationProject.enums.StatusEnum;
import facilizationProject.facilizationProject.service.IService.ITaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final ITaskService iTaskService;

    public TaskController(ITaskService iTaskService) {
        this.iTaskService = iTaskService;
    }

    @PostMapping("/projects/{projectId}/tasks")
    public ResponseEntity<TaskDTO> createTask(@PathVariable long projectId, @Valid @RequestBody TaskDTO taskDTO){
        return new ResponseEntity<>(iTaskService.createTask(projectId, taskDTO), HttpStatus.CREATED);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id){
        return ResponseEntity.ok(iTaskService.getTaskById(id));
    }

    @GetMapping("/projects/{projectId}/tasks")
    public ResponseEntity<Page<TaskDTO>> getTaskByProjects(
            @PathVariable Long projectId,
            @RequestParam(required = false)StatusEnum statusEnum,
            Pageable pageable
            ){
        return ResponseEntity.ok(iTaskService.getTasksByProjects(projectId, statusEnum, pageable));
    }

    @PutMapping("/task/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @Valid @RequestBody TaskDTO taskDTO){
        return ResponseEntity.ok(iTaskService.updateTask(id, taskDTO));
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        iTaskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tasks/due-today")
    public ResponseEntity<List<TaskDTO>> getTaskDueToday(){
        return ResponseEntity.ok(iTaskService.getTaskDueToday());
    }

    @GetMapping("/users/{userId}/tasks")
    public ResponseEntity<List<TaskDTO>> getTaskByAssignee(@PathVariable Long userId){
        return ResponseEntity.ok(iTaskService.getAllTasksByUser(userId));
    }
}
