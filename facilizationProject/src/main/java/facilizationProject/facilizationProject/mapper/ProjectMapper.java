package facilizationProject.facilizationProject.mapper;

import facilizationProject.facilizationProject.Entity.ProjectEntity;
import facilizationProject.facilizationProject.dto.ProjectDTO;

public class ProjectMapper {

    public static ProjectDTO toDTO(ProjectEntity projectEntity){
        if(projectEntity == null){
            return null;
        }

        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(projectEntity.getId());
        projectDTO.setName(projectEntity.getName());
        projectDTO.setDescription(projectEntity.getDescription());
        projectDTO.setCreatedAt(projectEntity.getCreatedAt());

        if(projectEntity.getOwner() != null){
            projectDTO.setOwnerId(UserMapper.toDTO(projectEntity.getOwner()));
        }
        return projectDTO;
    }

    public static ProjectEntity toEntity(ProjectDTO projectDTO){
        if(projectDTO == null){
            return null;
        }

        ProjectEntity entity = new ProjectEntity();
        entity.setId(projectDTO.getId());
        entity.setName(projectDTO.getName());
        entity.setDescription(projectDTO.getDescription());
        entity.setCreatedAt(projectDTO.getCreatedAt());

        return entity;
    }
}
