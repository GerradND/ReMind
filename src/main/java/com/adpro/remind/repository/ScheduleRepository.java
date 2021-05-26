package com.adpro.remind.repository;

import com.adpro.remind.model.Guild;
import com.adpro.remind.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    Schedule findByIdSchedule(Integer idSchedule);
    Iterable<Schedule> findByGuild(Guild guild);
    Iterable<Schedule> findByDay(DayOfWeek day);
}
