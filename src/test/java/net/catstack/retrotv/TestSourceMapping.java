package net.catstack.retrotv;

import net.catstack.retrotv.dto.request.source.AddSourceRequestDto;
import net.catstack.retrotv.dto.request.source.SourceFilters;
import net.catstack.retrotv.entity.source.SourceModel;
import net.catstack.retrotv.repository.HeadingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TestSourceMapping {
    @Autowired
    ModelMapper modelMapper;

    @MockBean
    HeadingRepository repository;

    @BeforeEach
    public void init() {
        when(repository.getByName("SomeHeading")).thenReturn(SampleObjects.getHeadingModel());
    }

    @Test
    public void testDtoToEntityMapping() {
        var addSourceRequestDto = new AddSourceRequestDto();

        addSourceRequestDto.setName("testname");
        addSourceRequestDto.setLink("https://catstack.net/");
        addSourceRequestDto.setHeading("SomeHeading");

        var sourceFilters = new SourceFilters();

        sourceFilters.setFirstDate("1");
        sourceFilters.setLastDate("2");
        sourceFilters.setPhrase("this is filter phrase");
        sourceFilters.setFirstN(2);
        sourceFilters.setLastN(5);

        addSourceRequestDto.setFilters(sourceFilters);

        var model = modelMapper.map(addSourceRequestDto, SourceModel.class);

        assertEquals(addSourceRequestDto.getName(), model.getName());
        assertEquals(addSourceRequestDto.getLink(), model.getLink());
        assertEquals(SampleObjects.getHeadingModel(), model.getHeading());

        assertEquals(sourceFilters.getFirstDate(), model.getFilters().getFirstDate());
        assertEquals(sourceFilters.getLastDate(), model.getFilters().getLastDate());
        assertEquals(sourceFilters.getFirstN(), model.getFilters().getFirstN());
        assertEquals(sourceFilters.getLastN(), model.getFilters().getLastN());
        assertEquals(sourceFilters.getPhrase(), model.getFilters().getPhrase());
    }
}
