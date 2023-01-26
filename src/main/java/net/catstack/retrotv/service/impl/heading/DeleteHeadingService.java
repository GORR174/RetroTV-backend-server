package net.catstack.retrotv.service.impl.heading;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catstack.retrotv.annotations.LoggingAspect;
import net.catstack.retrotv.dto.request.heading.DeleteHeadingRequestDto;
import net.catstack.retrotv.dto.response.StatusMessageResponseDto;
import net.catstack.retrotv.repository.HeadingRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteHeadingService {
    private final HeadingRepository repository;

    @LoggingAspect
    public StatusMessageResponseDto deleteHeading(final DeleteHeadingRequestDto request) {
        repository.findById(request.getId()).ifPresent(repository::delete);

        return new StatusMessageResponseDto(0, "Success");
    }
}
