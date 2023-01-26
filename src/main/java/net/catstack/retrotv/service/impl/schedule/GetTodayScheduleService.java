package net.catstack.retrotv.service.impl.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catstack.retrotv.annotations.LoggingAspect;
import net.catstack.retrotv.dto.request.heading.CreateHeadingRequestDto;
import net.catstack.retrotv.dto.response.schedule.GetScheduleResponseDto;
import net.catstack.retrotv.dto.response.schedule.GetTodayScheduleResponseDto;
import net.catstack.retrotv.dto.response.schedule.ScheduleItemDto;
import net.catstack.retrotv.repository.DailyScheduleRepository;
import net.catstack.retrotv.repository.HeadingRepository;
import net.catstack.retrotv.repository.SourceRepository;
import net.catstack.retrotv.repository.TodayScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetTodayScheduleService {
    private final TodayScheduleRepository repository;
    private final SourceRepository sourceRepository;
    private final HeadingRepository headingRepository;

    @LoggingAspect
    public GetTodayScheduleResponseDto getTodaySchedule() {
        var todayItems = repository.findAllByTimeIsNotNullOrderByTime();

        var response = new GetTodayScheduleResponseDto();
        var todayList = new ArrayList<ScheduleItemDto>();

        todayItems.forEach(item -> {
            var dto = new ScheduleItemDto();
            sourceRepository.findById(item.getSourceId())
                            .ifPresent(source -> {
                                dto.setSourceName(source.getName());
                            });
            headingRepository.findById(item.getHeadingId()).ifPresent(heading -> {
                dto.setHeading(new CreateHeadingRequestDto(heading.getName(), heading.getColor()));
            });
            dto.setTime(item.getTime());
            dto.setVideoName(item.getName());

            todayList.add(dto);
        });

        response.setScheduleItems(todayList);

        return response;
    }
}
