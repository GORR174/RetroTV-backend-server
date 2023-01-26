package net.catstack.retrotv.repository;

import net.catstack.retrotv.entity.schedule.HourSlotModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HourSlotRepository extends JpaRepository<HourSlotModel, Long> {
    HourSlotModel getByHour(int hour);
}
