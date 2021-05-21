package com.adpro.remind.repository;

import com.adpro.remind.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    Schedule findByIdSchedule(Integer idSchedule);
    Iterable<Schedule> findByDay(String day);
}
=======
import java.time.DayOfWeek;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    Schedule findByIdSchedule(Integer idSchedule);
    Iterable<Schedule> findByDay(DayOfWeek day);
}
>>>>>>> 2f72d3489f745ec377c15a4e5e4e3ffd915820f4
