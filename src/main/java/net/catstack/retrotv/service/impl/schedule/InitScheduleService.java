package net.catstack.retrotv.service.impl.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catstack.retrotv.annotations.LoggingAspect;
import net.catstack.retrotv.entity.schedule.DailyScheduleModel;
import net.catstack.retrotv.entity.schedule.HourSlotModel;
import net.catstack.retrotv.repository.DailyScheduleRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Service
@Slf4j
@RequiredArgsConstructor
public class InitScheduleService {
    private final DailyScheduleRepository repository;

    @LoggingAspect
    @PostConstruct
    public void initSchedule() {
        if (repository.count() > 0)
            return;

        log.info("Creating default schedule");

        var schedule = new ArrayList<DailyScheduleModel>();

        for (int dayNumber = 1; dayNumber <= 7; dayNumber++) {
            var dailyScheduleModel = new DailyScheduleModel();
            dailyScheduleModel.setDayNumber(dayNumber);
            dailyScheduleModel.setStartTime("06:00");
            dailyScheduleModel.setEndTime("02:00");

            var slots = new ArrayList<HourSlotModel>();
            for (int hourNumber = 0; hourNumber < 24; hourNumber++) {
                var hourModel = new HourSlotModel();

                hourModel.setHour(hourNumber);
                hourModel.setHeadings(new ArrayList<>());
                hourModel.setDayModel(dailyScheduleModel);

                slots.add(hourModel);
            }

            dailyScheduleModel.setSlots(slots);

            schedule.add(dailyScheduleModel);
        }

        repository.saveAll(schedule);
    }
}
