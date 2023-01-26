package net.catstack.retrotv.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import net.catstack.retrotv.dto.AdapterResponse;
import net.catstack.retrotv.dto.response.StatusMessageResponseDto;
import net.catstack.retrotv.service.impl.video.DownloadVideosService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@Api(tags = "Видео")
@RestController
@RequiredArgsConstructor
@RequestMapping("/videos")
public class VideoController {
    private final DownloadVideosService downloadVideosService;

    @Operation(summary = "Принудительно запустить загрузку видео")
    @GetMapping("/download")
    public AdapterResponse<StatusMessageResponseDto> downloadVideos() {
        CompletableFuture.runAsync(downloadVideosService::downloadVideos);

        return new AdapterResponse<>(new StatusMessageResponseDto(0, "Downloading started"));
    }
}
