package facilizationProject.facilizationProject.repository;

import facilizationProject.facilizationProject.Entity.TaskEntity;
import facilizationProject.facilizationProject.enums.StatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    @Query("SELECT t FROM TaskEntity  t WHERE t.dueDate = CURRENT_DATE")
    List<TaskEntity> findTaskEntityByDueToday();

    Page<TaskEntity> findByIdProjectTask_IdAndStatusEnum(Long projectId, StatusEnum statusEnum, Pageable pageable);

    Page<TaskEntity> findByIdProjectTask_Id(Long projectId, StatusEnum statusEnum, Pageable pageable);
    Page<TaskEntity> findByIdProjectTask_Id(Long projectId, Pageable pageable);
    List<TaskEntity> findByAssignee_Id(Long userId);

}
