package com.adpro.remind.repository;

import com.adpro.remind.model.Guild;
import com.adpro.remind.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    Task findByIdTask(Integer idTask);
    Iterable<Task> findByGuild(Guild guild);
    Iterable<Task> findByDate(LocalDate date);
}
