package facilizationProject.facilizationProject.service;

import facilizationProject.facilizationProject.Entity.ProjectEntity;
import facilizationProject.facilizationProject.Entity.UserEntity;
import facilizationProject.facilizationProject.dto.ProjectDTO;
import facilizationProject.facilizationProject.exception.BadRequestException;
import facilizationProject.facilizationProject.exception.ResourceNotFoundException;
import facilizationProject.facilizationProject.mapper.ProjectMapper;
import facilizationProject.facilizationProject.repository.ProjectRepository;
import facilizationProject.facilizationProject.repository.UserRepository;
import facilizationProject.facilizationProject.service.IService.IProjectService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class ProjectService implements IProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        if(projectRepository.existsByName(projectDTO.getName())){
            throw new BadRequestException("This project name already exists");
        }

        ProjectEntity projectEntity = ProjectMapper.toEntity(projectDTO);
        projectEntity.setCreatedAt(LocalDateTime.now());

        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity ownerEntity = userRepository.findByUsername(loggedInUsername)
                .orElseThrow(()-> new ResourceNotFoundException("Authenticated user not found in database"));

        projectEntity.setOwner(ownerEntity);

        ProjectEntity savedProject = projectRepository.save(projectEntity);
        return ProjectMapper.toDTO(savedProject);
    }

    @Override
    public ProjectDTO getProjectById(Long id) {
        return projectRepository.findById(id)
            .map(ProjectMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("There is no project with this id: " + id));
    }

    @Override
    public Page<ProjectDTO> getAllProjects(Pageable pageable) {
        return projectRepository.findAll(pageable)
                .map(ProjectMapper::toDTO);
    }

    @Override
    public ProjectDTO updateProject(Long id, ProjectDTO dto) {
        ProjectEntity projectEntity  = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found wit this id"));

        projectEntity.setName(dto.getName());
        projectEntity.setDescription(dto.getDescription());

        ProjectEntity updatedProject = projectRepository.save(projectEntity);
        return ProjectMapper.toDTO(updatedProject);
    }

    @Override
    public void deleteProject(Long id) {
        ProjectEntity projectEntity = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with this id: " + id));

        projectRepository.deleteById(id);
    }
}
