package net.catstack.retrotv.config;

import lombok.RequiredArgsConstructor;
import net.catstack.retrotv.mapper.BasePropertyMap;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ModelMapperConfig {
    private final List<BasePropertyMap> propertyMaps;

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        propertyMaps.forEach(modelMapper::addMappings);

        return modelMapper;
    }
}
