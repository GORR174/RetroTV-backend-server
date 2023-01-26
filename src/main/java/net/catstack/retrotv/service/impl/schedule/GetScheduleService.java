package net.catstack.retrotv.service.impl.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catstack.retrotv.annotations.LoggingAspect;
import net.catstack.retrotv.dto.response.schedule.GetScheduleResponseDto;
import net.catstack.retrotv.repository.DailyScheduleRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetScheduleService {
    private final DailyScheduleRepository repository;

    @LoggingAspect
    public GetScheduleResponseDto getSchedule() {
        var dailyScheduleModel = repository.findAll();

        var response = new GetScheduleResponseDto();

        response.setSchedule(dailyScheduleModel);

        return response;
    }
}
