package net.catstack.retrotv.repository;

import net.catstack.retrotv.entity.schedule.TodayScheduleModel;
import net.catstack.retrotv.entity.source.SourceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodayScheduleRepository extends JpaRepository<TodayScheduleModel, Long> {
    List<TodayScheduleModel> findBySourceIdAndTimeIsNull(Long sourceId);
    List<TodayScheduleModel> findByTimeIsNull();
    List<TodayScheduleModel> findAllByTimeIsNotNullOrderByTime();
}
