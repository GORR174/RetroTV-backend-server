package net.catstack.retrotv.service.impl.source;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catstack.retrotv.annotations.LoggingAspect;
import net.catstack.retrotv.dto.request.source.AddSourceRequestDto;
import net.catstack.retrotv.dto.response.StatusMessageResponseDto;
import net.catstack.retrotv.entity.source.SourceFilterModel;
import net.catstack.retrotv.exception.DataDoesntExistsException;
import net.catstack.retrotv.repository.HeadingRepository;
import net.catstack.retrotv.repository.SourceRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EditSourceService {
    private final SourceRepository repository;
    private final HeadingRepository headingRepository;

    @LoggingAspect
    public StatusMessageResponseDto editSource(final AddSourceRequestDto requestDto) {
        repository.findById(requestDto.getId()).ifPresentOrElse(sourceModel -> {
            sourceModel.setName(requestDto.getName());
            sourceModel.setLink(requestDto.getLink());

            var heading = headingRepository.getByName(requestDto.getHeading());

            if (heading == null) {
                throw new DataDoesntExistsException("Heading '" + requestDto.getHeading() + "' doesn't exists");
            }

            sourceModel.setHeading(heading);
            var filters = sourceModel.getFilters();
            if (filters == null && requestDto.getFilters() != null) {
                sourceModel.setFilters(new SourceFilterModel());
                filters = sourceModel.getFilters();
            }
            if (filters != null && requestDto.getFilters() != null) {
                sourceModel.setFilters(new SourceFilterModel());
                filters.setFirstDate(requestDto.getFilters().getFirstDate());
                filters.setLastDate(requestDto.getFilters().getLastDate());
                filters.setPhrase(requestDto.getFilters().getPhrase());
                filters.setFirstN(requestDto.getFilters().getFirstN());
                filters.setLastN(requestDto.getFilters().getLastN());
            }

            if (filters != null && requestDto.getFilters() == null) {
                sourceModel.setFilters(null);
            }

            repository.save(sourceModel);
        }, () -> {
            throw new DataDoesntExistsException("Can't find source with ID: " + requestDto.getId());
        });

        return new StatusMessageResponseDto(0, "Success");
    }
}
