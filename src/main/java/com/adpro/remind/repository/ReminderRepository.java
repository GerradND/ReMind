package com.adpro.remind.repository;

import com.adpro.remind.model.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder,Integer> {
    Reminder findByIdReminder(Integer id);
}
