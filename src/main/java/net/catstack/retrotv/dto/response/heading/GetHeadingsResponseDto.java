package net.catstack.retrotv.dto.response.heading;

import lombok.Data;
import net.catstack.retrotv.entity.heading.HeadingModel;

import java.util.List;

@Data
public class GetHeadingsResponseDto {
    private List<HeadingModel> headings;
}
