package net.catstack.retrotv.dto.response.source;

import lombok.Data;
import net.catstack.retrotv.entity.source.SourceModel;

import java.util.List;

@Data
public class GetSourcesResponseDto {
    private List<SourceModel> sources;
}
