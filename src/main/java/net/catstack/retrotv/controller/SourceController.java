package net.catstack.retrotv.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import net.catstack.retrotv.dto.AdapterResponse;
import net.catstack.retrotv.dto.request.source.AddSourceRequestDto;
import net.catstack.retrotv.dto.request.source.DeleteSourceRequestDto;
import net.catstack.retrotv.dto.response.StatusMessageResponseDto;
import net.catstack.retrotv.dto.response.source.GetSourcesResponseDto;
import net.catstack.retrotv.service.impl.source.CreateSourceService;
import net.catstack.retrotv.service.impl.source.DeleteSourceService;
import net.catstack.retrotv.service.impl.source.EditSourceService;
import net.catstack.retrotv.service.impl.source.GetSourcesService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "Источники")
@RestController
@RequiredArgsConstructor
@RequestMapping("/sources")
public class SourceController {
    private final CreateSourceService createSourceService;
    private final GetSourcesService getSourcesService;
    private final DeleteSourceService deleteSourceService;
    private final EditSourceService editSourceService;

    @Operation(summary = "Получить список источников")
    @GetMapping("/getSources")
    public AdapterResponse<GetSourcesResponseDto> getSources() {
        return new AdapterResponse<>(getSourcesService.getSources());
    }

    @Operation(summary = "Создать или изменить источник")
    @PostMapping("/createSource")
    public AdapterResponse<StatusMessageResponseDto> createSource(@RequestBody @Valid AddSourceRequestDto request) {
        if (request.getId() == null) {
            return new AdapterResponse<>(createSourceService.createSource(request));
        } else {
            return new AdapterResponse<>(editSourceService.editSource(request));
        }
    }

    @Operation(summary = "Удалить источник")
    @DeleteMapping("/deleteSource")
    public AdapterResponse<StatusMessageResponseDto> deleteSource(@RequestBody @Valid DeleteSourceRequestDto request) {
        return new AdapterResponse<>(deleteSourceService.deleteSource(request));
    }

}
