package facilizationProject.facilizationProject.mapper;

import facilizationProject.facilizationProject.Entity.TaskEntity;
import facilizationProject.facilizationProject.dto.TaskDTO;

public class TaskMapper {

    public static TaskDTO toDTO(TaskEntity taskEntity){
        if(taskEntity == null){
            return null;
        }

        TaskDTO dto = new TaskDTO();
        dto.setId(taskEntity.getId());
        dto.setTitle(taskEntity.getTitle());
        dto.setDescription(taskEntity.getDescription());
        dto.setDueDate(taskEntity.getDueDate());
        dto.setCreatedAt(taskEntity.getCreatedAt());

        dto.setPriority(taskEntity.getPriorityEnum());
        dto.setStatus(taskEntity.getStatusEnum());

        if(taskEntity.getIdProjectTask() != null){
            dto.setProjectDTO(ProjectMapper.toDTO(taskEntity.getIdProjectTask()));
        }

        if(taskEntity.getAssignee() != null){
            dto.setUserDTO(UserMapper.toDTO(taskEntity.getAssignee()));
        }

        return dto;
    }

    public static TaskEntity toEntity(TaskDTO dto){
        if(dto == null){
            return null;
        }

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(dto.getId());
        taskEntity.setTitle(dto.getTitle());
        taskEntity.setDescription(dto.getDescription());
        taskEntity.setDueDate(dto.getDueDate());
        taskEntity.setCreatedAt(dto.getCreatedAt());

        taskEntity.setPriorityEnum(dto.getPriority());
        taskEntity.setStatusEnum(dto.getStatus());

        return  taskEntity;
    }

}
