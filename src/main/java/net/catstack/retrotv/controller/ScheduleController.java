package net.catstack.retrotv.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import net.catstack.retrotv.dto.AdapterResponse;
import net.catstack.retrotv.dto.request.heading.CreateHeadingRequestDto;
import net.catstack.retrotv.dto.request.schedule.SetSlotHeadingRequestDto;
import net.catstack.retrotv.dto.response.StatusMessageResponseDto;
import net.catstack.retrotv.dto.response.schedule.GetScheduleResponseDto;
import net.catstack.retrotv.dto.response.schedule.GetTodayScheduleResponseDto;
import net.catstack.retrotv.dto.response.schedule.ScheduleItemDto;
import net.catstack.retrotv.service.impl.schedule.GetScheduleService;
import net.catstack.retrotv.service.impl.schedule.GetTodayScheduleService;
import net.catstack.retrotv.service.impl.schedule.SetSlotHeadingsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Расписание")
@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {
    private final GetScheduleService getScheduleService;
    private final SetSlotHeadingsService setSlotHeadingsService;
    private final GetTodayScheduleService getTodayScheduleService;

    @Operation(summary = "Получить расписание слотов")
    @GetMapping("/getSchedule")
    public AdapterResponse<GetScheduleResponseDto> getSchedule() {
        return new AdapterResponse<>(getScheduleService.getSchedule());
    }

    @Operation(summary = "Получить расписание видео на сегодня")
    @GetMapping("/getTodaySchedule")
    public AdapterResponse<GetTodayScheduleResponseDto> getTodaySchedule() {
        return new AdapterResponse<>(getTodayScheduleService.getTodaySchedule());
    }

    @Operation(summary = "Установить рубрики для слота")
    @PostMapping("/setSlotHeading")
    public AdapterResponse<StatusMessageResponseDto> setSlotHeadings(@RequestBody @Valid SetSlotHeadingRequestDto requestDto) {
        return new AdapterResponse<>(setSlotHeadingsService.setSlotHeadings(requestDto));
    }
}
