package facilizationProject.facilizationProject.controller;

import facilizationProject.facilizationProject.Entity.ProjectEntity;
import facilizationProject.facilizationProject.dto.ProjectDTO;
import facilizationProject.facilizationProject.service.IService.IProjectService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final IProjectService iProjectService;

    public ProjectController(IProjectService iProjectService) {
        this.iProjectService = iProjectService;
    }

    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@Valid @RequestBody ProjectDTO projectDTO){
        return new ResponseEntity<>(iProjectService.createProject(projectDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Long id){
        return ResponseEntity.ok(iProjectService.getProjectById(id));
    }

    @GetMapping
    public ResponseEntity<Page<ProjectDTO>> getAllProjects(Pageable pageable){
        return ResponseEntity.ok(iProjectService.getAllProjects(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable Long id, @Valid @RequestBody ProjectDTO projectDTO0){
        return ResponseEntity.ok(iProjectService.updateProject(id, projectDTO0));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id){
        iProjectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
