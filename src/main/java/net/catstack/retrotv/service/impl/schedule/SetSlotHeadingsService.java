package net.catstack.retrotv.service.impl.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catstack.retrotv.annotations.LoggingAspect;
import net.catstack.retrotv.dto.request.schedule.SetSlotHeadingRequestDto;
import net.catstack.retrotv.dto.response.StatusMessageResponseDto;
import net.catstack.retrotv.dto.response.schedule.GetScheduleResponseDto;
import net.catstack.retrotv.repository.DailyScheduleRepository;
import net.catstack.retrotv.repository.HeadingRepository;
import net.catstack.retrotv.repository.HourSlotRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class SetSlotHeadingsService {
    private final DailyScheduleRepository dailyRepository;
    private final HourSlotRepository hourRepository;
    private final HeadingRepository headingRepository;

    @LoggingAspect
    public StatusMessageResponseDto setSlotHeadings(final SetSlotHeadingRequestDto request) {
        var dayModel = dailyRepository.getByDayNumber(request.getDayNumber());
        var hourModel = dayModel.getSlots().stream()
                .filter(slot -> slot.getHour() == request.getHour())
                .findFirst().orElseThrow();

        hourModel.setHeadings(new ArrayList<>(request.getHeadings().stream().map(headingRepository::getByName).toList()));

        hourRepository.save(hourModel);

        return new StatusMessageResponseDto(0, "Success");
    }
}
