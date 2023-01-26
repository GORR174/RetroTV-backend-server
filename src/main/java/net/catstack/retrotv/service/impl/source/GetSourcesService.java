package net.catstack.retrotv.service.impl.source;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catstack.retrotv.annotations.LoggingAspect;
import net.catstack.retrotv.dto.response.source.GetSourcesResponseDto;
import net.catstack.retrotv.repository.SourceRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetSourcesService {
    private final SourceRepository repository;

    @LoggingAspect
    public GetSourcesResponseDto getSources() {
        var sourceModels = repository.findAll();

        var response = new GetSourcesResponseDto();

        response.setSources(sourceModels);

        return response;
    }
}
