package net.catstack.retrotv.service.impl.heading;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catstack.retrotv.annotations.LoggingAspect;
import net.catstack.retrotv.dto.response.heading.GetHeadingsResponseDto;
import net.catstack.retrotv.repository.HeadingRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetHeadingsService {
    private final HeadingRepository repository;

    @LoggingAspect
    public GetHeadingsResponseDto getHeadings() {
        var response = new GetHeadingsResponseDto();

        response.setHeadings(repository.findAll());

        return response;
    }
}
