package com.adpro.remind.repository;

import com.adpro.remind.model.Guild;
import com.adpro.remind.model.Task;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    Task findByIdTask(Integer idTask);
    Iterable<Task> findByGuild(Guild guild);
    Iterable<Task> findByDateAndGuild(LocalDate date, Guild guild);
}
