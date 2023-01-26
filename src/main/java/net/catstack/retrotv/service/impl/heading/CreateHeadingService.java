package net.catstack.retrotv.service.impl.heading;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catstack.retrotv.annotations.LoggingAspect;
import net.catstack.retrotv.dto.response.StatusMessageResponseDto;
import net.catstack.retrotv.entity.heading.HeadingModel;
import net.catstack.retrotv.dto.request.heading.CreateHeadingRequestDto;
import net.catstack.retrotv.exception.AlreadyExistsException;
import net.catstack.retrotv.repository.HeadingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateHeadingService {
    private final HeadingRepository repository;
    private final ModelMapper modelMapper;

    @LoggingAspect
    public StatusMessageResponseDto createHeading(final CreateHeadingRequestDto request) {
        if (repository.getByName(request.getName()) != null) {
            throw new AlreadyExistsException("Heading already exists");
        }

        var model = modelMapper.map(request, HeadingModel.class);

        repository.save(model);

        return new StatusMessageResponseDto(0, "Success");
    }
}
