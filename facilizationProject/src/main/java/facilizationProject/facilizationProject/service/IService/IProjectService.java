package facilizationProject.facilizationProject.service.IService;

import facilizationProject.facilizationProject.dto.ProjectDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProjectService {

    ProjectDTO createProject(ProjectDTO projectDTO);

    ProjectDTO getProjectById(Long id);

    Page<ProjectDTO> getAllProjects(Pageable pageable);

    ProjectDTO updateProject(Long id, ProjectDTO dto);

    void deleteProject(Long id);
}
