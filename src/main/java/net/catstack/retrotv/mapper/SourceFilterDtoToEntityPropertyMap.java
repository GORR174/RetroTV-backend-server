package net.catstack.retrotv.mapper;

import lombok.RequiredArgsConstructor;
import net.catstack.retrotv.dto.request.source.AddSourceRequestDto;
import net.catstack.retrotv.entity.heading.HeadingModel;
import net.catstack.retrotv.entity.source.SourceModel;
import net.catstack.retrotv.exception.DataDoesntExistsException;
import net.catstack.retrotv.repository.HeadingRepository;
import org.modelmapper.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SourceFilterDtoToEntityPropertyMap extends BasePropertyMap<AddSourceRequestDto, SourceModel> {
    private final HeadingRepository headingRepository;

    @Override
    protected void configure() {
        using((Converter<String, HeadingModel>) context -> {
            var heading = headingRepository.getByName(context.getSource());

            if (heading == null) {
                throw new DataDoesntExistsException("Heading '" + context.getSource() + "' doesn't exists");
            }

            return heading;
        })
                .map(source.getHeading())
                .setHeading(null);
    }
}
