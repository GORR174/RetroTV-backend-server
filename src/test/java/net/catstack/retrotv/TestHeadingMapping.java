package net.catstack.retrotv;

import net.catstack.retrotv.dto.request.heading.CreateHeadingRequestDto;
import net.catstack.retrotv.entity.heading.HeadingModel;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestHeadingMapping {
    @Autowired
    ModelMapper modelMapper;

    @Test
    public void testDtoToEntityMapping() {
        var createHeadingRequestDto = new CreateHeadingRequestDto();

        createHeadingRequestDto.setName("Test Name");
        createHeadingRequestDto.setColor(2);

        var model = modelMapper.map(createHeadingRequestDto, HeadingModel.class);

        assertEquals(createHeadingRequestDto.getName(), model.getName());
        assertEquals(createHeadingRequestDto.getColor(), model.getColor());
    }
}

