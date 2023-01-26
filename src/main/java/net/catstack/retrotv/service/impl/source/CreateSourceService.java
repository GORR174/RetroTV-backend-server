package net.catstack.retrotv.service.impl.source;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catstack.retrotv.annotations.LoggingAspect;
import net.catstack.retrotv.dto.request.source.AddSourceRequestDto;
import net.catstack.retrotv.dto.response.StatusMessageResponseDto;
import net.catstack.retrotv.entity.source.SourceModel;
import net.catstack.retrotv.exception.AlreadyExistsException;
import net.catstack.retrotv.repository.SourceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateSourceService {
    private final SourceRepository repository;
    private final ModelMapper modelMapper;

    @LoggingAspect
    public StatusMessageResponseDto createSource(final AddSourceRequestDto request) {
        if (repository.getByName(request.getName()) != null) {
            throw new AlreadyExistsException("Source already exists");
        }

        var model = modelMapper.map(request, SourceModel.class);

        repository.save(model);

        return new StatusMessageResponseDto(0, "Success");
    }
}
