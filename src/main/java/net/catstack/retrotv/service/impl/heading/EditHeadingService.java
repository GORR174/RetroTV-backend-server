package net.catstack.retrotv.service.impl.heading;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catstack.retrotv.annotations.LoggingAspect;
import net.catstack.retrotv.dto.request.heading.CreateHeadingRequestDto;
import net.catstack.retrotv.dto.response.StatusMessageResponseDto;
import net.catstack.retrotv.exception.DataDoesntExistsException;
import net.catstack.retrotv.repository.HeadingRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EditHeadingService {
    private final HeadingRepository repository;

    @LoggingAspect
    public StatusMessageResponseDto editHeading(final CreateHeadingRequestDto requestDto) {
        repository.findById(requestDto.getId()).ifPresentOrElse(headingModel -> {
            headingModel.setName(requestDto.getName());
            headingModel.setColor(requestDto.getColor());

            repository.save(headingModel);
        }, () -> {
            throw new DataDoesntExistsException("Can't find heading with ID: " + requestDto.getId());
        });

        return new StatusMessageResponseDto(0, "Success");
    }
}
