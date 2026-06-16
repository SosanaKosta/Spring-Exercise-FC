package facilizationProject.facilizationProject.service;

import facilizationProject.facilizationProject.Entity.ProjectEntity;
import facilizationProject.facilizationProject.Entity.TaskEntity;
import facilizationProject.facilizationProject.Entity.UserEntity;
import facilizationProject.facilizationProject.dto.TaskDTO;
import facilizationProject.facilizationProject.enums.StatusEnum;
import facilizationProject.facilizationProject.exception.ResourceNotFoundException;
import facilizationProject.facilizationProject.mapper.TaskMapper;
import facilizationProject.facilizationProject.repository.ProjectRepository;
import facilizationProject.facilizationProject.repository.TaskRepository;
import facilizationProject.facilizationProject.repository.UserRepository;
import facilizationProject.facilizationProject.service.IService.ITaskService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskService implements ITaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public TaskDTO createTask(Long projectId, TaskDTO dto) {
        ProjectEntity projectEntity = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id " +projectId));

        TaskEntity taskEntity = TaskMapper.toEntity(dto);
        taskEntity.setCreatedAt(LocalDateTime.now());
        taskEntity.setIdProjectTask(projectEntity);

        if(dto.getUserDTO() != null && dto.getUserDTO().getId() != null){
            UserEntity assigne = userRepository.findById(dto.getUserDTO().getId())
                    .orElseThrow(()-> new ResourceNotFoundException("Assigne user not found with id " + dto.getUserDTO().getId()));
            taskEntity.setAssignee(assigne);
        }

        TaskEntity savedTask = taskRepository.save(taskEntity);
        return TaskMapper.toDTO(savedTask);
    }

    @Override
    public TaskDTO getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(TaskMapper::toDTO)
                .orElseThrow(()-> new ResourceNotFoundException("Task not found with id: " + id));
    }

    @Override
    public Page<TaskDTO> getTasksByProjects(Long projectId, StatusEnum statusEnum, Pageable pageable) {
        if(!projectRepository.existsById(projectId)){
            throw new ResourceNotFoundException("Project not found with id: " + projectId);
        }

        if(statusEnum != null){
            return taskRepository.findByIdProjectTask_Id(projectId, statusEnum, pageable)
                    .map(TaskMapper:: toDTO);
        }

        return taskRepository.findByIdProjectTask_Id(projectId, pageable)
                .map(TaskMapper::toDTO);
    }

    @Override
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        taskDTO.setId(id);
        TaskEntity taskEntity1 = TaskMapper.toEntity(taskDTO);

        taskEntity1.setCreatedAt(taskEntity.getCreatedAt());
        taskEntity1.setIdProjectTask(taskEntity.getIdProjectTask());

        if(taskDTO.getUserDTO() != null && taskDTO.getUserDTO().getId() != null){
            UserEntity assigne = userRepository.findById(taskDTO.getUserDTO().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Assignee not found"));
            taskEntity1.setAssignee(assigne);
        }else {
            taskEntity1.setAssignee(null);
        }
        return TaskMapper.toDTO(taskRepository.save(taskEntity1));
    }

    @Override
    public void deleteTask(Long id) {
        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        taskRepository.delete(taskEntity);
    }

    @Override
    public List<TaskDTO> getTaskDueToday() {
        return taskRepository.findTaskEntityByDueToday()
                .stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getAllTasksByUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        return taskRepository.findByAssignee_Id(userId)
                .stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }
}
