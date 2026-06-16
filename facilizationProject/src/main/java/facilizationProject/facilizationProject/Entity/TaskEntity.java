package facilizationProject.facilizationProject.Entity;

import facilizationProject.facilizationProject.enums.PriorityEnum;
import facilizationProject.facilizationProject.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Data
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private PriorityEnum priorityEnum;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    private StatusEnum statusEnum;

    @Column(name = "dueDate")
    private LocalDate dueDate;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private ProjectEntity idProjectTask;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id")
    private UserEntity assignee;
}
