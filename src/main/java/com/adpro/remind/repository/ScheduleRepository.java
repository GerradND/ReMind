package com.adpro.remind.repository;

import com.adpro.remind.model.Guild;
import com.adpro.remind.model.Schedule;
import java.time.DayOfWeek;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    Schedule findByIdSchedule(Integer idSchedule);
    List<Schedule> findAllByGuild(Guild guild);
    List<Schedule> findByDayAndGuild(DayOfWeek day, Guild guild);
}
