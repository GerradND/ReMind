package com.adpro.remind.repository;

import com.adpro.remind.model.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReminderRepository extends JpaRepository<Reminder,Integer> {

}
