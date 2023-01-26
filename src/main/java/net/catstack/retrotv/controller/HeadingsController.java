package net.catstack.retrotv.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import net.catstack.retrotv.dto.AdapterResponse;
import net.catstack.retrotv.dto.request.heading.CreateHeadingRequestDto;
import net.catstack.retrotv.dto.request.heading.DeleteHeadingRequestDto;
import net.catstack.retrotv.dto.response.StatusMessageResponseDto;
import net.catstack.retrotv.dto.response.heading.GetHeadingsResponseDto;
import net.catstack.retrotv.service.impl.heading.CreateHeadingService;
import net.catstack.retrotv.service.impl.heading.DeleteHeadingService;
import net.catstack.retrotv.service.impl.heading.EditHeadingService;
import net.catstack.retrotv.service.impl.heading.GetHeadingsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "Рубрики")
@RestController
@RequiredArgsConstructor
@RequestMapping("/headings")
public class HeadingsController {
    private final CreateHeadingService createHeadingService;
    private final GetHeadingsService getHeadingsService;
    private final DeleteHeadingService deleteHeadingService;
    private final EditHeadingService editHeadingService;

    @Operation(summary = "Получить список рубрик")
    @GetMapping("/getHeadings")
    public AdapterResponse<GetHeadingsResponseDto> getHeadings() {
        return new AdapterResponse<>(getHeadingsService.getHeadings());
    }

    @Operation(summary = "Создать или изменить рубрику")
    @PostMapping("/createHeading")
    public AdapterResponse<StatusMessageResponseDto> createHeading(@RequestBody @Valid CreateHeadingRequestDto request) {
        if (request.getId() == null) {
            return new AdapterResponse<>(createHeadingService.createHeading(request));
        } else {
            return new AdapterResponse<>(editHeadingService.editHeading(request));
        }
    }

    @Operation(summary = "Удалить рубрику")
    @DeleteMapping("/deleteHeading")
    public AdapterResponse<StatusMessageResponseDto> deleteHeading(@RequestBody @Valid DeleteHeadingRequestDto request) {
        return new AdapterResponse<>(deleteHeadingService.deleteHeading(request));
    }
}
