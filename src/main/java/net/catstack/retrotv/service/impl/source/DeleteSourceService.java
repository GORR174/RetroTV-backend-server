package net.catstack.retrotv.service.impl.source;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catstack.retrotv.annotations.LoggingAspect;
import net.catstack.retrotv.dto.request.source.DeleteSourceRequestDto;
import net.catstack.retrotv.dto.response.StatusMessageResponseDto;
import net.catstack.retrotv.repository.SourceRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteSourceService {
    private final SourceRepository repository;

    @LoggingAspect
    public StatusMessageResponseDto deleteSource(final DeleteSourceRequestDto request) {
        repository.findById(request.getId()).ifPresent(repository::delete);

        return new StatusMessageResponseDto(0, "Success");
    }
}
