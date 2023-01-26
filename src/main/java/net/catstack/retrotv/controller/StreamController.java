package net.catstack.retrotv.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import net.catstack.retrotv.dto.AdapterResponse;
import net.catstack.retrotv.dto.response.StatusMessageResponseDto;
import net.catstack.retrotv.service.StreamerService;
import net.catstack.retrotv.service.impl.video.DownloadVideosService;
import org.modelmapper.internal.Pair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

@Api(tags = "Стримы")
@RestController
@RequiredArgsConstructor
@RequestMapping("/streams")
public class StreamController {
    private final StreamerService streamerService;

    @Operation(summary = "Принудительно запустить стрим")
    @GetMapping("/startStream")
    public AdapterResponse<StatusMessageResponseDto> startStream() {
        streamerService.startStream();

        return new AdapterResponse<>(new StatusMessageResponseDto(0, "Stream has been started"));
    }

    @Operation(summary = "Принудительно завершить стрим")
    @GetMapping("/stopStream")
    public AdapterResponse<StatusMessageResponseDto> stopStream() {
        streamerService.stopStream();

        return new AdapterResponse<>(new StatusMessageResponseDto(0, "Stream has been started"));
    }

    @Operation(summary = "Проверить запущен ли стрим")
    @GetMapping("/isOnline")
    public AdapterResponse<HashMap<String, Boolean>> isOnline() {
        return new AdapterResponse<>(new HashMap<>() {{ put("isOnline", streamerService.isOnline()); }});
    }
}
