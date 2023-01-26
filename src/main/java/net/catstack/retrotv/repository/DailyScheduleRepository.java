package net.catstack.retrotv.repository;

import net.catstack.retrotv.entity.schedule.DailyScheduleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyScheduleRepository extends JpaRepository<DailyScheduleModel, Long> {
    DailyScheduleModel getByDayNumber(int dayNumber);
}
